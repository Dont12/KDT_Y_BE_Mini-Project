package com.fastcampus.reserve.interfaces.auth.dto.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {

}
