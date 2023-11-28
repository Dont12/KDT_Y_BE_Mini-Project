package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartCommand cartCommand;

    @Transactional
    public void addItem(User user, CartItemAddDto dto) {
        var cart = dto.toEntity();
        cart.registerUser(user);
        cartCommand.store(cart);
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
}
