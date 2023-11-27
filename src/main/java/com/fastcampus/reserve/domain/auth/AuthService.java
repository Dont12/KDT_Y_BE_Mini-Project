package com.fastcampus.reserve.domain.auth;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import com.fastcampus.reserve.domain.auth.dto.request.LoginDto;
import com.fastcampus.reserve.domain.auth.dto.response.LoginTokenDto;
import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final AuthCommand authCommand;

    public LoginTokenDto login(User user, LoginDto dto) {
        matchingPassword(dto.password(), user.getPassword());
        Token token = createToken(user);
        return LoginTokenDto.from(token);
    }

    private void matchingPassword(String password, String encodePassword) {
        if (!isMatchingPassword(password, encodePassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private boolean isMatchingPassword(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }

    private Token createToken(User user) {
        var token = jwtProvider.generateToken(user);
        authCommand.store(token);
        return token;
    }
}
