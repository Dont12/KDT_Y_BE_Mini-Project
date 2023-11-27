package com.fastcampus.reserve.common.security.config;

import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import com.fastcampus.reserve.common.utils.CookieUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    public static final String AUTHORIZATION_COOKIE_NAME = "accessToken";
    public static final String GRANT_TYPE = "Bearer";
    private final JwtProvider jwtProvider;

    private static String getAuthorization(HttpServletRequest request) {
        String accessToken = CookieUtils.getCookieValue(
            request.getCookies(), AUTHORIZATION_COOKIE_NAME
        ).orElse("");
        return GRANT_TYPE + " " + accessToken;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = getAuthorization(httpServletRequest);
        String token = jwtProvider.resolveToken(authorization);
        if (isValidToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        return StringUtils.hasText(token) && jwtProvider.validate(token);
    }
}
