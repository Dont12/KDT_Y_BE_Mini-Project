package com.fastcampus.reserve.domain.user.dto.response;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record CartDetailItemDto(
        Long id,
        RoomDto product,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        long numberOfNights,
        int guestCount
) {

    public static CartDetailItemDto from(CartItemDto cartItem, RoomDto roomDto) {
        return new CartDetailItemDto(
                cartItem.id(),
                roomDto,
                cartItem.checkInDate(),
                cartItem.checkOutDate(),
                ChronoUnit.DAYS.between(cartItem.checkInDate(), cartItem.checkOutDate()),
                cartItem.guestCount()
        );
    }

    public Long roomId() {
        return product.roomId();
    }
}
