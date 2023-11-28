package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.RegisterOrderFactory;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import com.fastcampus.reserve.domain.user.dto.response.CartItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private static final long INIT_ORDER_EXPIRED_TIME = 600;

    private final RegisterOrderFactory registerOrderFactory;
    private final CartCommand cartCommand;
    private final CartReader cartReader;
    private final RedisService redisService;

    @Transactional
    public void addItem(User user, CartItemAddDto dto) {
        var cart = dto.toEntity();
        cart.registerUser(user);
        cartCommand.store(cart);
    }

    public Page<CartItemDto> getCartItems(User user, Pageable pageable) {
        Page<Cart> cartItems = cartReader.getCartItems(user, pageable);
        return cartItems.map(CartItemDto::from);
    }

    @Transactional
    public void deleteItems(User user, CartItemDeleteDto dto) {
        List<Long> cartIds = dto.cartIds();

        for (Long cartId : cartIds) {
            if (!cartCommand.isValid(user, cartId)) {
                throw new CustomException(
                    String.format("id: %d", cartId),
                    ErrorCode.INVALID_CART_ITEM
                );
            }
            cartCommand.deleteById(cartId);
        }
    }

    public CartItemDto getCartItem(Long cartId) {
        return CartItemDto.from(cartReader.getCartItem(cartId));
    }
}
