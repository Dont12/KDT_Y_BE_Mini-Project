package com.fastcampus.reserve.domain.user.dto.response;

public record PageDto(
    int size,
    int maxPage,
    long totalCount
) {
}
