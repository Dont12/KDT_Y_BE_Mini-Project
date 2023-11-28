package com.fastcampus.reserve.application.user;

import com.fastcampus.reserve.domain.product.ProductService;
import com.fastcampus.reserve.domain.user.CartService;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.response.CartDetailItemDto;
import com.fastcampus.reserve.domain.user.dto.response.CartItemDto;
import com.fastcampus.reserve.domain.user.dto.response.CartListDto;
import com.fastcampus.reserve.domain.user.dto.response.RoomDetailDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public void addItem(Long userId, CartItemAddDto dto) {
        var user = userService.getUser(userId);
        cartService.addItem(user, dto);
    }

    public CartListDto getList(Long userId, Pageable pageable) {
        var user = userService.getUser(userId);

        Page<CartDetailItemDto> cartItems =
            cartService.getCartItems(user, pageable).map(this::toDetail);
        List<Long> roomIds = cartItems.stream().map(CartDetailItemDto::roomId).toList();

        int totalPrice = productService.calcTotalPrice(roomIds);
        return CartListDto.from(cartItems, totalPrice);
    }

    private CartDetailItemDto toDetail(CartItemDto cartItemDto) {
        RoomDetailDto roomDetailDto =
            productService.getRoomDetail(cartItemDto.roomId());
        return CartDetailItemDto.from(cartItemDto, roomDetailDto);
    }
}
