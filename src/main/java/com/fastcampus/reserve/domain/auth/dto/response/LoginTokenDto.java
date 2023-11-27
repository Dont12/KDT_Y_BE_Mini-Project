package com.fastcampus.reserve.domain.auth.dto.response;

import com.fastcampus.reserve.domain.auth.Token;

public record LoginTokenDto(
    String accessToken,
    String refreshToken
) {

    public static LoginTokenDto from(Token token) {
        return new LoginTokenDto(
            token.getAccessToken(),
            token.getRefreshToken()
        );
    }
}
