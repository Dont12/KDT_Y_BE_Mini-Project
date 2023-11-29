package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderHistoriesDto(
        Long orderId,
        LocalDate reserveDate,
        List<OrderItemInfoDto> orderItems
) {

    public static OrderHistoriesDto from(Order order) {
        return OrderHistoriesDto.builder()
                .orderId(order.getId())
                .reserveDate(order.getCreatedDate().toLocalDate())
                .orderItems(getOrderItems(order.getOrderItems()))
                .build();
    }

    private static List<OrderItemInfoDto> getOrderItems(List<OrderItem> order) {
        return order.stream()
                .map(OrderItemInfoDto::from)
                .toList();
    }
}
