package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.order.payment.Payment;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderInfoDto(
        Long orderId,
        String reserveName,
        String reservePhone,
        String userName,
        String userPhone,
        int totalPrice,
        LocalDate reserveDate,
        Payment payment,
        List<OrderItemInfoDto> orderItems
) {

    public static OrderInfoDto from(Order order) {
        return OrderInfoDto.builder()
                .orderId(order.getId())
                .reserveName(order.getReserveName())
                .reservePhone(order.getReservePhone())
                .userName(order.getUserName())
                .userPhone(order.getUserPhone())
                .totalPrice(order.calcTotalPrice())
                .reserveDate(order.getCreatedDate().toLocalDate())
                .payment(order.getPayment())
                .orderItems(getOrderItems(order.getOrderItems()))
                .build();
    }

    private static List<OrderItemInfoDto> getOrderItems(List<OrderItem> order) {
        return order.stream()
                .map(OrderItemInfoDto::from)
                .toList();
    }
}
