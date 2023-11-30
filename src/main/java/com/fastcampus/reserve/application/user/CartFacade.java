package com.fastcampus.reserve.application.user;

import com.fastcampus.reserve.domain.order.OrderService;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderItemDto;
import com.fastcampus.reserve.domain.product.ProductService;
import com.fastcampus.reserve.domain.user.CartService;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import com.fastcampus.reserve.domain.user.dto.response.CartDetailItemDto;
import com.fastcampus.reserve.domain.user.dto.response.CartItemDto;
import com.fastcampus.reserve.domain.user.dto.response.CartListDto;
import com.fastcampus.reserve.domain.user.dto.response.CartDetailDto;
import com.fastcampus.reserve.interfaces.user.dto.request.CartOrderDto;
import java.util.ArrayList;
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
    private final OrderService orderService;

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
        CartDetailDto roomDetailDto =
            productService.getRoomDetail(cartItemDto.roomId());
        return CartDetailItemDto.from(cartItemDto, roomDetailDto);
    }

    public void deleteItems(Long userId, CartItemDeleteDto dto) {
        var user = userService.getUser(userId);
        cartService.deleteItems(user, dto);
    }

    public String order(Long userId, CartOrderDto dto) {
        List<RegisterOrderItemDto> items = new ArrayList<>();
        for (Long cartId : dto.cartIds()) {
            items.add(toRegisterOrderItemDto(cartId));
        }

        RegisterOrderDto orderDto = new RegisterOrderDto(
            userId,
            items
        );
        return orderService.registerOrder(orderDto);
    }

    private RegisterOrderItemDto toRegisterOrderItemDto(Long cartId) {
        CartItemDto cart = cartService.getCartItem(cartId);

        CartDetailDto roomDetailDto =
            productService.getRoomDetail(cart.roomId());

        return new RegisterOrderItemDto(
            roomDetailDto.productId(),
            roomDetailDto.roomId(),
            cart.checkInDate(),
            roomDetailDto.checkInTime(),
            cart.checkOutDate(),
            roomDetailDto.checkOutTime(),
            cart.guestCount(),
            roomDetailDto.price()
        );
    }
}
