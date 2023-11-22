package com.fastcampus.reserve.common.security;

import lombok.Builder;

@Builder
public record Token(
        String grantType,
        String accessToken,
        String refreshToken
) {

}
