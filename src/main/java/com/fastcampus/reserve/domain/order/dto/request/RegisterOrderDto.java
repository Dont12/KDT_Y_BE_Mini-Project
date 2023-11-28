package com.fastcampus.reserve.domain.order.dto.request;

import static com.fastcampus.reserve.common.utils.ValidateUtils.emptyCheck;

import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.user.User;
import java.util.List;

public record RegisterOrderDto(
        Long userId,
        List<RegisterOrderItemDto> registerOrderItems
) {

    public RegisterOrderDto {
        emptyCheck(registerOrderItems);
    }

    public RegisterOrder toEntity(User user) {
        return RegisterOrder.builder()
                .userId(userId)
                .name(user.getNickname())
                .phone(user.getPhone())
                .orderItems(getOrderItems())
                .build();
    }

    private List<OrderItem> getOrderItems() {
        return registerOrderItems.stream()
                .map(RegisterOrderItemDto::toEntity)
                .toList();
    }
}
