package com.fastcampus.reserve.interfaces.order.dto.response;

import java.time.LocalDate;

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
        String checkInTime,
        LocalDate checkInDate,
        String checkOutTime,
        LocalDate checkOutDate
) {

}
