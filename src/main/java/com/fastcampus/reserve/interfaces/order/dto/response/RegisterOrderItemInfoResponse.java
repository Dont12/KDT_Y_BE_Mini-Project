package com.fastcampus.reserve.interfaces.order.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegisterOrderItemInfoResponse(
        Long productId,
        String productName,
        String imageUrl,
        Long roomId,
        String roomName,
        Integer guestCount,
        Integer maxGuestCount,
        Integer baseGuestCount,
        Integer price,
        LocalTime checkInTime,
        LocalDate checkInDate,
        LocalTime checkOutTime,
        LocalDate checkOutDate
) {

}
