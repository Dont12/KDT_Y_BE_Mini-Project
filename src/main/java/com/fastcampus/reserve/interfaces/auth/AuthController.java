package com.fastcampus.reserve.interfaces.auth;

import com.fastcampus.reserve.application.auth.AuthFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.auth.dto.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthFacade authFacade;
    private final AuthDtoMapper mapper;

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(
        @RequestBody @Valid LoginRequest request
    ) {
        var loginToken = authFacade.login(mapper.of(request));
        return CommonResponse.ok(mapper.of(loginToken));
    }
}
