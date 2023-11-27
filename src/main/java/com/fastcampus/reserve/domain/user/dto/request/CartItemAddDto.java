package com.fastcampus.reserve.domain.user.dto.request;

import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.domain.user.User;
import java.time.LocalDate;

public record CartItemAddDto(
    Long roomId,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Integer guestCount
) {

    public Cart toEntity() {
        return Cart.builder()
            .roomId(roomId)
            .checkInDate(checkInDate)
            .checkOutDate(checkOutDate)
            .guestCount(guestCount)
            .build();
    }
}
