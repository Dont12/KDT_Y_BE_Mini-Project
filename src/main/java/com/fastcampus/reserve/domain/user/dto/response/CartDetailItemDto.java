package com.fastcampus.reserve.domain.user.dto.response;

import java.time.LocalDate;
import java.time.Period;

public record CartDetailItemDto(
    Long id,
    RoomDto product,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    int numberOfNights,
    int guestCount
) {

    public static CartDetailItemDto from(CartItemDto cartItem, RoomDto roomDto) {
        return new CartDetailItemDto(
            cartItem.id(),
            roomDto,
            cartItem.checkInDate(),
            cartItem.checkOutDate(),
            Period.between(cartItem.checkInDate(), cartItem.checkOutDate()).getDays(),
            cartItem.guestCount()
        );
    }

    public Long roomId() {
        return product.roomId();
    }
}
