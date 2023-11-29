package com.fastcampus.reserve.interfaces.order.dto.response;

import java.time.LocalDate;
import java.util.List;

public record OrderHistoryResponse(
        Long orderId,
        LocalDate reserveDate,
        List<OrderItemInfoResponse> orderItems
) {

}
