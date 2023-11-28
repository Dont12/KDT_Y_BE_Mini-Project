package com.fastcampus.reserve.interfaces.user.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CartItemAddRequest(
    @NotNull(message = "roomId는 필수값입니다.")
    Long roomId,
    @NotNull(message = "checkInDate는 필수값입니다.")
    LocalDate checkInDate,
    @NotNull(message = "checkOutDate는 필수값입니다.")
    LocalDate checkOutDate,
    @NotNull(message = "guestCount는 필수값입니다.")
    Integer guestCount
) {
}
