package com.fastcampus.reserve.common.utils;

import jakarta.servlet.http.Cookie;
import java.util.Optional;

public class CookieUtils {

    private CookieUtils() {

    }

    public static Optional<String> getCookieValue(Cookie[] cookies, String name) {
        if (cookies == null) {
            return Optional.empty();
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }
}
