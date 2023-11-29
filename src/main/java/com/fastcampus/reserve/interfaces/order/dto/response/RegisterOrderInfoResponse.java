package com.fastcampus.reserve.interfaces.order.dto.response;

import java.util.List;

public record RegisterOrderInfoResponse(
        String orderToken,
        int totalPrice,
        String name,
        String phone,
        List<RegisterOrderItemInfoResponse> registerOrderItems
) {

}
