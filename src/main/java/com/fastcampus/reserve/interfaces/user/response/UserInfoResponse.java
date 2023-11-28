package com.fastcampus.reserve.interfaces.user.response;

public record UserInfoResponse(
    String email,
    String nickname,
    String phone
) {
}
