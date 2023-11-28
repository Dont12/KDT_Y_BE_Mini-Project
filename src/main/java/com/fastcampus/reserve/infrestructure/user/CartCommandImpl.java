package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.domain.user.CartCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CartCommandImpl implements CartCommand {

    private final CartRepository cartRepository;

    @Override
    public void store(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {

    }
}
