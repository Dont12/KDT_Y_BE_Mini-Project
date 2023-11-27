package com.fastcampus.reserve.interfaces.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.infrestructure.user.UserRepository;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class UserControllerTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void signup() {
        // given
        String email = "a@a.com";
        SignupRequest request = new SignupRequest(
            email,
            "password",
            "nickname",
            "010-0000-0000"
        );

        String url = "/v1/users";

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post(url)
                .then().log().all()
                .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(userRepository.existsByEmail(email)).isEqualTo(true);
    }

    @Test
    void myInfo() {
        // given
        String email = "a@a.com";
        String password = "password";
        userService.signup(new SignupDto(email, password, "nick", "010-0000-0000"));

        LoginRequest request = new LoginRequest(email, password);

        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/v1/auth/login")
            .then().extract();

        String url = "/v1/users";

        // when
        ExtractableResponse<Response> result = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .cookies(loginResponse.cookies())
            .get(url)
            .then().log().all()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
