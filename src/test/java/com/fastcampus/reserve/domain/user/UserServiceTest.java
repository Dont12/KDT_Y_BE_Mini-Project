package com.fastcampus.reserve.domain.user;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserCommand userCommand;
    @Mock
    private UserReader userReader;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("중복 이메일이 아닐경우 예외가 발생하지 않음")
    void signup_success() {
        // given
        SignupDto dto = new SignupDto(
            "a@a.com",
            "password",
            "nickname",
            "010-0000-0000"
        );
        when(userReader.existsByEmail(any(String.class)))
            .thenReturn(false);

        // when, then
        assertThatNoException().isThrownBy(
            () -> userService.signup(dto)
        );
    }

    @Test
    @DisplayName("중복 이메일이라면 예외 발생")
    void signup_fail() {
        // given
        SignupDto dto = new SignupDto(
            "a@a.com",
            "password",
            "nickname",
            "010-0000-0000"
        );
        when(userReader.existsByEmail(any()))
            .thenReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.signup(dto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.DUPLICATED_EMAIL.getMessage());
    }
}
