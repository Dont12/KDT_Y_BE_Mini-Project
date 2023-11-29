package com.fastcampus.reserve.domain.user.dto.response;

import com.fastcampus.reserve.domain.user.User;

public record UserInfoDto(
        String email,
        String nickname,
        String phone
) {

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getEmail(),
                user.getNickname(),
                user.getPhone()
        );
    }
}
