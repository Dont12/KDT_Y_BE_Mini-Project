package com.fastcampus.reserve.domain.user.dto.response;

import java.time.LocalDate;
import java.time.Period;

public record CartDetailItemDto(
    Long id,
    CartDetailDto product,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    int numberOfNights,
    int guestCount
) {

    public static CartDetailItemDto from(CartItemDto cartItem, CartDetailDto roomDetailDto) {
        return new CartDetailItemDto(
            cartItem.id(),
            roomDetailDto,
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
