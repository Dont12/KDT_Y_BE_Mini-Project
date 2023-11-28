package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterOrderInfoDto(
        String orderToken,
        int totalPrice,
        String name,
        String phone,
        List<RegisterOrderItemInfoDto> registerOrderItems
) {

    public static RegisterOrderInfoDto from(
            String orderToken,
            RegisterOrder registerOrder
    ) {
        return RegisterOrderInfoDto.builder()
                .orderToken(orderToken)
                .totalPrice(registerOrder.calcTotalPrice())
                .name(registerOrder.name())
                .phone(registerOrder.phone())
                .registerOrderItems(getRegisterOrderItems(registerOrder.orderItems()))
                .build();
    }

    private static List<RegisterOrderItemInfoDto> getRegisterOrderItems(
            List<OrderItem> orderItems
    ) {
        return orderItems.stream()
                .map(RegisterOrderItemInfoDto::from)
                .toList();
    }
}
