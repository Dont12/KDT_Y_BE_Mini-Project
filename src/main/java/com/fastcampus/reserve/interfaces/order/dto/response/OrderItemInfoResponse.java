package com.fastcampus.reserve.interfaces.order.dto.response;

import java.time.LocalDate;

public record OrderItemInfoResponse(
        Long orderItemId,
        Long productId,
        String productName,
        String roomName,
        String imageUrl,
        Integer maxGuestCount,
        Integer baseGuestCount,
        long day,
        String checkInTime,
        LocalDate checkInDate,
        String checkOutTime,
        LocalDate checkOutDate
) {

}
