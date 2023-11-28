package com.fastcampus.reserve.domain.dto.request;

import java.time.LocalDate;

public record ProductListOptionDto(
    LocalDate checkIn,
    LocalDate checkOut,
    String category,
    String areaCode,
    int page,
    int pageSize
) {
}
