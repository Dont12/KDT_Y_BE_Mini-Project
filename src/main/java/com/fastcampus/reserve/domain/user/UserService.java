package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserCommand userCommand;
    private final UserReader userReader;

    public void signup(SignupDto dto) {
        if (userReader.existsByEmail(dto.email())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        var user = dto.toEntity();
        userCommand.store(user);
    }
}
