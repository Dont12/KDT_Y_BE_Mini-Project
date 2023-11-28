package com.fastcampus.reserve.infrestructure.user.cart;

import com.fastcampus.reserve.domain.user.CartCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartCommandImpl implements CartCommand {

    @Override
    public void deleteById(Long id) {

    }
}
