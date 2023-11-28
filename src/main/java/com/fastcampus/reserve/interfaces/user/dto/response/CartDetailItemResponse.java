package com.fastcampus.reserve.interfaces.user.dto.response;

import com.fastcampus.reserve.domain.user.dto.response.RoomDetailDto;
import java.time.LocalDate;

public record CartDetailItemResponse(
    Long id,
    RoomDetailDto product,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    int numberOfNights,
    int guestCount
) {
}
