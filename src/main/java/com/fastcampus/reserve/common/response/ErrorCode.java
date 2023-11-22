package com.fastcampus.reserve.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_USER_ROLE(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 사용자 역할입니다."),
    NOT_AUTHORITY_TOKEN(HttpStatus.UNAUTHORIZED, "권한이 없는 토큰 정보입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
