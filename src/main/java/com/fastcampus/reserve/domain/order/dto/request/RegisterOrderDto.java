package com.fastcampus.reserve.domain.order.dto.request;

import static com.fastcampus.reserve.common.utils.ValidateUtils.emptyCheck;

import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.util.List;

public record RegisterOrderDto(
        Long userId,
        List<RegisterOrderItemDto> registerOrderItems
) {

    public RegisterOrderDto {
        emptyCheck(registerOrderItems);
    }

    public RegisterOrder toEntity() {
        return RegisterOrder.builder()
                .userId(userId)
                .orderItems(getOrderItems())
                .build();
    }

    private List<OrderItem> getOrderItems() {
        return registerOrderItems.stream()
                .map(RegisterOrderItemDto::toEntity)
                .toList();
    }
}
