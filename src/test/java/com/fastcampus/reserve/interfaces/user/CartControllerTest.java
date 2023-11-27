package com.fastcampus.reserve.interfaces.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.application.auth.AuthFacade;
import com.fastcampus.reserve.application.user.UserFacade;
import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.domain.auth.dto.request.LoginDto;
import com.fastcampus.reserve.domain.auth.dto.response.LoginTokenDto;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.infrestructure.user.CartRepository;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CartControllerTest extends ApiTest {

    @Autowired
    private UserFacade userFacade;
    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void addItem() {
        // given
        String email = "email@a.com";
        String password = "password";

        SignupDto signupDto = new SignupDto(email, password, "nick", "010-0000-0000");
        userFacade.signup(signupDto);

        LoginDto loginDto = new LoginDto(email, password);
        LoginTokenDto loginTokenDto = authFacade.login(loginDto);
        Cookie loginCookie = new Cookie("accessToken", loginTokenDto.accessToken());

        Long roomId = 1L;
        LocalDate checkInTime = LocalDate.of(2010, 1, 1);
        LocalDate checkOutTime = LocalDate.of(2010, 1, 2);
        int guestCount = 4;
        CartItemAddRequest request = new CartItemAddRequest(
            roomId,
            checkInTime,
            checkOutTime,
            guestCount
        );

        String url = "/v1/carts";

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(loginCookie.getName(), loginCookie.getValue())
                .body(request)
                .when()
                .post(url)
                .then().log().all()
                .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());

        var cart = cartRepository.findById(1L).orElseThrow();
        assertThat(cart.getRoomId()).isEqualTo(roomId);
        assertThat(cart.getUser().getId()).isEqualTo(1L);
        assertThat(cart.getGuestCount()).isEqualTo(guestCount);
        assertThat(cart.getCheckInDate()).isEqualTo(checkInTime);
        assertThat(cart.getCheckOutDate()).isEqualTo(checkOutTime);
    }
}
