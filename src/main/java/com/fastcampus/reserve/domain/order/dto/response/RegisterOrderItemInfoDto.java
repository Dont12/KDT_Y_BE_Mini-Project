package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RegisterOrderItemInfoDto(
        Long productId,
        String productName,
        Long roomId,
        String roomName,
        String imageUrl,
        Integer guestCount,
        Integer maxGuestCount,
        Integer baseGuestCount,
        Integer price,
        String checkInTime,
        LocalDate checkInDate,
        String checkOutTime,
        LocalDate checkOutDate
) {

    public static RegisterOrderItemInfoDto from(OrderItem orderItem) {
        return RegisterOrderItemInfoDto.builder()
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .roomId(orderItem.getRoomId())
                .roomName(orderItem.getRoomName())
                .imageUrl(orderItem.getImageUrl())
                .guestCount(orderItem.getGuestCount())
                .baseGuestCount(orderItem.getBaseGuestCount())
                .maxGuestCount(orderItem.getMaxGuestCount())
                .price(orderItem.getPrice())
                .checkInDate(orderItem.getCheckInDate())
                .checkInTime(orderItem.getCheckInTime())
                .checkOutDate(orderItem.getCheckOutDate())
                .checkOutTime(orderItem.getCheckOutTime())
                .build();
    }
}
