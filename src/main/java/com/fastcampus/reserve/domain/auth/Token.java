package com.fastcampus.reserve.domain.auth;

import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "token", timeToLive = 86400)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    private Long id;

    private String refreshToken;

    @Transient
    private String grantType;

    @Transient
    private String accessToken;

    @Builder
    private Token(
        Long id,
        String grantType,
        String accessToken,
        String refreshToken
    ) {
        this.id = id;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
