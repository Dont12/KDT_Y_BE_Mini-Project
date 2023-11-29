package com.fastcampus.reserve.interfaces.order.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record OrderItemInfoResponse(
        Long orderItemId,
        Long productId,
        String productName,
        String roomName,
        String imageUrl,
        Integer maxGuestCount,
        Integer baseGuestCount,
        LocalTime checkInTime,
        LocalDate checkInDate,
        LocalTime checkOutTime,
        LocalDate checkOutDate
) {

}
