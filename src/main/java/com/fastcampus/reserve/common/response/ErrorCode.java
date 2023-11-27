package com.fastcampus.reserve.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시스템 오류입니다."),
    COMMON_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    INVALID_USER_ROLE(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 사용자 역할입니다."),
    NOT_AUTHORITY_TOKEN(HttpStatus.UNAUTHORIZED, "권한이 없는 토큰 정보입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    NO_SUCH_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
