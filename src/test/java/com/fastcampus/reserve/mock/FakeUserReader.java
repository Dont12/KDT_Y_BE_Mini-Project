package com.fastcampus.reserve.mock;

import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class FakeUserReader implements UserReader {

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return User.builder()
                .email("email@gamil.com")
                .password("password")
                .nickname("nickname")
                .phone("phone")
                .build();
    }
}
