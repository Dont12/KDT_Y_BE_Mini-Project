package com.fastcampus.reserve.domain.order.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReserveDateDto(
        Long roomId,
        LocalDate date
) {

    public String getLockName() {
        return roomId + "_" + date;
    }
}
