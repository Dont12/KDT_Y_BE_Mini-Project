package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserReaderImpl implements UserReader {

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
