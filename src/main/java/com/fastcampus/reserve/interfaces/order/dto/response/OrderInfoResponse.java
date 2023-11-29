package com.fastcampus.reserve.interfaces.order.dto.response;

import com.fastcampus.reserve.domain.order.payment.Payment;
import java.time.LocalDate;
import java.util.List;

public record OrderInfoResponse(
        Long orderId,
        String reserveName,
        String reservePhone,
        String userName,
        String userPhone,
        int totalPrice,
        LocalDate reserveDate,
        Payment payment,
        List<OrderItemInfoResponse> orderItems
) {

}
