package com.fastcampus.reserve.interfaces.user.dto.response;

public record PageResponse(
    int size,
    int maxPage,
    long totalCount
) {
}
