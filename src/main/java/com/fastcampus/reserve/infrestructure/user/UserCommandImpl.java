package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommandImpl implements UserCommand {

    private final UserRepository userRepository;

    @Override
    public void store(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
