package com.fastcampus.reserve.application.auth;

import com.fastcampus.reserve.domain.auth.AuthService;
import com.fastcampus.reserve.domain.auth.dto.request.LoginDto;
import com.fastcampus.reserve.domain.auth.dto.response.LoginTokenDto;
import com.fastcampus.reserve.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;
    private final UserService userService;

    public LoginTokenDto login(LoginDto dto) {
        var user = userService.getUser(dto.email());
        return authService.login(user, dto);
    }
}
