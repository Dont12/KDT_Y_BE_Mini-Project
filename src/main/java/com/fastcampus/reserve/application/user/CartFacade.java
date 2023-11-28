package com.fastcampus.reserve.application.user;

import com.fastcampus.reserve.domain.user.CartService;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;
    private final UserService userService;

    public void addItem(Long userId, CartItemAddDto dto) {
        var user = userService.getUser(userId);
        cartService.addItem(user, dto);
    }
}
