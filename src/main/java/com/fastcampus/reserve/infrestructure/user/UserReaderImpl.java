package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_USER));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_USER));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_USER));
    }
}
