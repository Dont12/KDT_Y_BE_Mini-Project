package com.fastcampus.reserve.interfaces.order.dto.request;

import com.fastcampus.reserve.domain.order.payment.Payment;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull(message = "orderToken은 필수값 입니다.")
        String orderToken,
        @NotNull(message = "이용자 이름은 필수값 입니다.")
        String userName,
        @NotNull(message = "이용자 핸드폰 번호는 필수값 입니다.")
        String userPhone,
        @NotNull(message = "결제 금액은 필수값 입니다.")
        Integer price,
        @NotNull(message = "결제 수단은 필수값 입니다.")
        Payment payment
) {

}
