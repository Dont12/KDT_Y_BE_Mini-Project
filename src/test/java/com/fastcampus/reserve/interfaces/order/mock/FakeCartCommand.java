package com.fastcampus.reserve.interfaces.order.mock;

import com.fastcampus.reserve.domain.user.CartCommand;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class FakeCartCommand implements CartCommand {

    @Override
    public void deleteById(Long id) {

    }
}
