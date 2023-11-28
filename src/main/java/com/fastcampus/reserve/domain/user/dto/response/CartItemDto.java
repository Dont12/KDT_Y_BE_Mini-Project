package com.fastcampus.reserve.domain.user.dto.response;

import com.fastcampus.reserve.domain.user.Cart;
import java.time.LocalDate;

public record CartItemDto(
    Long id,
    Long roomId,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    int guestCount
) {

    public static CartItemDto from(Cart cart) {
        return new CartItemDto(
            cart.getId(),
            cart.getRoomId(),
            cart.getCheckInDate(),
            cart.getCheckOutDate(),
            cart.getGuestCount()
        );
    }
}
