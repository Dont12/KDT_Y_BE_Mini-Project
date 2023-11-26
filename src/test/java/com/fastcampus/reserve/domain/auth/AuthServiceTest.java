package com.fastcampus.reserve.domain.auth;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import com.fastcampus.reserve.domain.auth.dto.request.LoginDto;
import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.domain.user.UserCommand;
import com.fastcampus.reserve.domain.user.UserReader;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private AuthCommand authCommand;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우 예외")
    void login_fail_for_password() {
        // given
        User user = User.builder().build();
        LoginDto loginDto = new LoginDto(null,  null);

        when(passwordEncoder.matches(any(), any()))
            .thenReturn(false);

        // when, then
        assertThatThrownBy(() -> authService.login(user, loginDto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.INVALID_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("비밀번호 일치하면 로그인 성공")
    void login_success() {
        // given
        User user = User.builder().build();
        LoginDto loginDto = new LoginDto(null,  null);

        when(passwordEncoder.matches(any(), any()))
            .thenReturn(true);
        when(jwtProvider.generateToken(any()))
            .thenReturn(Token.builder().build()); // 안하면 NullPointerException 발생

        // when, then
        assertThatNoException()
            .isThrownBy(() -> authService.login(user, loginDto));
    }
}
