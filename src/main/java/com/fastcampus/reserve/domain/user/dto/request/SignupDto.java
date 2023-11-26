package com.fastcampus.reserve.domain.user.dto.request;

import com.fastcampus.reserve.domain.user.User;

public record SignupDto(
    String email,
    String password,
    String nickname,
    String phone
) {

    public User toEntity(String encodedPassword) {
        return User.builder()
            .email(email)
            .password(encodedPassword)
            .phone(phone)
            .nickname(nickname)
            .build();
    }
}
