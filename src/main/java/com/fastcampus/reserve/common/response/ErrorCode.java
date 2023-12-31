package com.fastcampus.reserve.common.response;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(INTERNAL_SERVER_ERROR, "시스템 오류입니다."),
    COMMON_INVALID_PARAMETER(BAD_REQUEST, "잘못된 요청입니다."),

    INVALID_USER_ROLE(INTERNAL_SERVER_ERROR, "잘못된 사용자 역할입니다."),
    NOT_AUTHORITY_TOKEN(UNAUTHORIZED, "권한이 없는 토큰 정보입니다."),
    DUPLICATED_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),
    NO_SUCH_USER(BAD_REQUEST, "존재하지 않는 사용자입니다"),
    INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_CHECK_IN_OUT_DATE(BAD_REQUEST, "체크아웃이 체크인보다 앞섭니다."),

    NOT_EXIST_REGISTER_ORDER(BAD_REQUEST, "존재하지 않는 예약 신청입니다."),
    NOT_EXIST_IMAGE(INTERNAL_SERVER_ERROR, "이미지가 존재하지 않습니다."),
    NOT_MATCH_TOTAL_PRICE(BAD_REQUEST, "결제 금액이 일치하지 않습니다."),
    INVALID_CART_ITEM(BAD_REQUEST, "장바구니 정보가 유효하지 않습니다"),
    NO_SUCH_ORDER(BAD_REQUEST, "존재하지 않는 주문 내역입니다."),

    NOT_EXIST_PRODUCT(BAD_REQUEST, "존재하지 않는 숙박 정보입니다."),
    NOT_EXIST_ROOM(BAD_REQUEST, "존재하지 않는 방 정보입니다."),
    ALREADY_ORDER_ROOM(BAD_REQUEST, "이미 예약된 방입니다."),

    REDIS_LOCK_ACQUISITION_FAILED(BAD_REQUEST, "Redisson Lock 획득에 실패하였습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
