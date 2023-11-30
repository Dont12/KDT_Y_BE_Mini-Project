package com.fastcampus.reserve.domain.order.dto.response;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        Long day,
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
                .day(ChronoUnit.MONTHS.between(
                        orderItem.getCheckInDate(),
                        orderItem.getCheckOutDate()
                ) + 1)
                .checkInDate(orderItem.getCheckInDate())
                .checkInTime(orderItem.getCheckInTime())
                .checkOutDate(orderItem.getCheckOutDate())
                .checkOutTime(orderItem.getCheckOutTime())
                .build();
    }
}
