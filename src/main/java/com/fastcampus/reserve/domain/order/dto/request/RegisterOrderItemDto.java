package com.fastcampus.reserve.domain.order.dto.request;

import static com.fastcampus.reserve.common.utils.ValidateUtils.nullCheck;

import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;

public record RegisterOrderItemDto(
        Long productId,
        Long roomId,
        LocalDate checkInDate,
        String checkInTime,
        LocalDate checkOutDate,
        String checkOutTime,
        Integer guestCount,
        Integer price
) {

    public RegisterOrderItemDto {
        nullCheck(
                productId,
                roomId,
                checkInDate,
                checkInTime,
                checkOutDate,
                checkOutTime,
                guestCount,
                price
        );
    }

    public OrderItem toEntity() {
        return OrderItem.builder()
                .productId(productId)
                .roomId(roomId)
                .checkInDate(checkInDate)
                .checkInTime(checkInTime)
                .checkOutDate(checkOutDate)
                .checkOutTime(checkOutTime)
                .guestCount(guestCount)
                .price(price)
                .build();
    }
}
