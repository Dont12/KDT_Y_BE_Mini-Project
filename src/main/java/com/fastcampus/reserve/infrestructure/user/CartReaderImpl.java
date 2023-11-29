package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.domain.user.CartReader;
import com.fastcampus.reserve.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CartReaderImpl implements CartReader {

    private final CartRepository cartRepository;

    @Override
    public Page<Cart> getCartItems(User user, Pageable pageable) {
        return cartRepository.findByUser(user, pageable);
    }
}
