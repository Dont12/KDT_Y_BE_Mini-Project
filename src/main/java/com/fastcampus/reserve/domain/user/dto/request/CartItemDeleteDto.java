package com.fastcampus.reserve.domain.user.dto.request;

import java.util.List;

public record CartItemDeleteDto(
    List<Long> cartIds
) {
}
