package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.response.CartItemDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartCommand cartCommand;
    private final CartReader cartReader;

    public void addItem(User user, CartItemAddDto dto) {
        var cart = dto.toEntity();
        cart.registerUser(user);
        cartCommand.store(cart);
    }

    public Page<CartItemDto> getCartItems(User user, Pageable pageable) {
        Page<Cart> cartItems = cartReader.getCartItems(user, pageable);
        return cartItems.map(CartItemDto::from);
    }
}
