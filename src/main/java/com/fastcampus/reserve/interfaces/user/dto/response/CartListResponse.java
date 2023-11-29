package com.fastcampus.reserve.interfaces.user.dto.response;

import com.fastcampus.reserve.domain.user.dto.response.CartDetailItemDto;
import java.util.List;

public record CartListResponse(
    PageResponse page,
    int totalPrice,
    List<CartDetailItemResponse> items
) {
}
