package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartCommand cartCommand;

    public void addItem(User user, CartItemAddDto dto) {
        var cart = dto.toEntity();
        cart.registerUser(user);
        cartCommand.store(cart);
    }
}
