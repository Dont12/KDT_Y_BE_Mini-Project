package com.fastcampus.reserve.domain.product.dto.request;

import java.time.LocalDate;

public record ProductListOptionDto(
    LocalDate checkIn,
    LocalDate checkOut,
    String category,
    String area,
    int page,
    int pageSize
) {
}
