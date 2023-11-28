package com.fastcampus.reserve.domain.order.dto.request;

import static com.fastcampus.reserve.common.utils.ValidateUtils.nullCheck;

import com.fastcampus.reserve.domain.order.payment.Payment;

public record PaymentDto(
        String orderToken,
        String userName,
        String userPhone,
        Integer price,
        Payment payment
) {

    public PaymentDto {
        nullCheck(
                orderToken,
                userName,
                userPhone,
                price,
                payment
        );
    }
}
