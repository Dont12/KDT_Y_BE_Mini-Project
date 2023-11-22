package com.fastcampus.reserve.domain.order.payment;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import java.util.Arrays;

public enum Payment {

    CARD,
    ;
    
    public static Payment of(String payMethod) {
        return Arrays.stream(Payment.values())
                .filter(payment -> payment.name().equals(payMethod))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ROLE));
    }
}
