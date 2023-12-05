package com.fastcampus.reserve.application.user;

import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.domain.user.dto.response.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public void signup(SignupDto signupDto) {
        userService.signup(signupDto);
    }

    public UserInfoDto getUserInfo(Long userId) {
        return userService.getUserInfo(userId);
    }

    public void terminateAccount(Long userId) {
        userService.terminateAccount(userId);
    }
}
