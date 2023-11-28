package com.fastcampus.reserve.domain.user.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record CartListDto(
    PageDto page,
    int totalPrice,
    List<CartDetailItemDto> items
) {

    public static CartListDto from(Page<CartDetailItemDto> cartItems, int totalPrice) {
        return new CartListDto(
            new PageDto(
                cartItems.getNumberOfElements(),
                cartItems.getTotalPages(),
                cartItems.getTotalElements()
            ),
            totalPrice,
            cartItems.getContent()
        );
    }
}
