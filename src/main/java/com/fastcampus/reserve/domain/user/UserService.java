package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.domain.user.dto.response.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserCommand userCommand;
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupDto dto) {
        if (userReader.existsByEmail(dto.email())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        var encodedPassword = passwordEncoder.encode(dto.password());
        var user = dto.toEntity(encodedPassword);
        userCommand.store(user);
    }

    public User getUser(String email) {
        return userReader.findByEmail(email);
    }

    public User getUser(Long id) {
        return userReader.findById(id);
    }

    public UserInfoDto getUserInfo(Long userId) {
        var user = userReader.findById(userId);
        return UserInfoDto.from(user);
    }

    @Transactional
    public void changePassword(Long userId, String passwordToChange) {
        var user = userReader.findById(userId);
        user.changePassword(passwordEncoder.encode(passwordToChange));
        userCommand.store(user);
    }
}
