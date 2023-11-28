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
    NO_SUCH_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_CHECK_IN_OUT_DATE(HttpStatus.BAD_REQUEST, "체크아웃이 체크인보다 앞섭니다."),

    NOT_EXIST_REGISTER_ORDER(HttpStatus.BAD_REQUEST, "존재하지 않는 예약 신청입니다."),
    NOT_EXIST_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지가 존재하지 않습니다."),
    NOT_MATCH_TOTAL_PRICE(HttpStatus.BAD_REQUEST, "결제 금액이 일치하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
