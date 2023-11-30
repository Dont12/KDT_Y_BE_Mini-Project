package com.fastcampus.reserve.interfaces.auth;

import com.fastcampus.reserve.application.auth.AuthFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.domain.auth.dto.response.LoginTokenDto;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public static final int ONE_HOUR_MAX_AGE = 60 * 60;
    public static final int TWO_WEEKS_MAX_AGE = 60 * 60 * 24 * 7 * 2;

    private final AuthFacade authFacade;
    private final AuthDtoMapper mapper;

    @PostMapping("/login")
    public CommonResponse<Void> login(
        @RequestBody @Valid LoginRequest request,
        HttpServletResponse response
    ) {
        var loginToken = authFacade.login(mapper.of(request));

        String accessCookie = getAccessCookie(loginToken);
        response.addHeader("Set-Cookie", accessCookie);

        String refreshCookie = getRefreshToken(loginToken);
        response.addHeader("Set-Cookie", refreshCookie);

        return CommonResponse.ok();
    }

    private String getRefreshToken(LoginTokenDto loginToken) {
        return makeCookie(
            "refreshToken",
            loginToken.refreshToken(),
            TWO_WEEKS_MAX_AGE
        );
    }

    private String getAccessCookie(LoginTokenDto loginToken) {
        return makeCookie(
            "accessToken",
            loginToken.accessToken(),
            ONE_HOUR_MAX_AGE
        );
    }

    private String makeCookie(String name, String value, int maxAge) {
        return String.format(
            "%s=%s; Path=/; Max-Age=%d; SameSite=None; Secure; Domain=localhost:3000",
            name, value, maxAge
        );
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(
        HttpServletResponse response
    ) {
        response.addHeader("Set-Cookie", makeCookieExpired("accessToken"));
        response.addHeader("Set-Cookie", makeCookieExpired("refreshToken"));

        return CommonResponse.ok();
    }

    private String makeCookieExpired(String name) {
        return String.format(
            "%s=; Path=/; Max-Age=0; SameSite=None; Secure",
            name
        );
    }
}
