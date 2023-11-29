package com.fastcampus.reserve.interfaces.user.dto.request;

import java.util.List;

public record CartOrderDto(
    List<Long> cartIds
) {
}
