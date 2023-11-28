package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record OrderItemInfoDto(
        Long orderItemId,
        Long productId,
        String productName,
        Long roomId,
        String roomName,
        String imageUrl,
        Integer guestCount,
        Integer maxGuestCount,
        Integer baseGuestCount,
        LocalTime checkInTime,
        LocalDate checkInDate,
        LocalTime checkOutTime,
        LocalDate checkOutDate
) {

    public static OrderItemInfoDto from(OrderItem orderItem) {
        return OrderItemInfoDto.builder()
                .orderItemId(orderItem.getId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .roomId(orderItem.getRoomId())
                .roomName(orderItem.getRoomName())
                .imageUrl(orderItem.getImageUrl())
                .guestCount(orderItem.getGuestCount())
                .maxGuestCount(orderItem.getMaxGuestCount())
                .baseGuestCount(orderItem.getBaseGuestCount())
                .checkInTime(orderItem.getCheckInTime())
                .checkInDate(orderItem.getCheckInDate())
                .checkOutTime(orderItem.getCheckOutTime())
                .checkOutDate(orderItem.getCheckOutDate())
                .build();
    }
}
