package com.fastcampus.reserve.interfaces.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class AuthControllerTest extends ApiTest {

    @Autowired
    private UserService userService;

    @Test
    void login() {
        String email = "a@a.com";
        String password = "password";
        userService.signup(new SignupDto(email, password, "nick", "010-0000-0000"));

        LoginRequest request = new LoginRequest(
            email,
            password
        );

        String url = "/v1/auth/login";

        // when
        ExtractableResponse<Response> result = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post(url)
            .then()
            .log().all()
            .cookie("accessToken")
            .cookie("refreshToken")
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void logout() {
        // given
        String email = "a@a.com";
        String password = "password";
        userService.signup(new SignupDto(email, password, "nick", "010-0000-0000"));
        LoginRequest request = new LoginRequest(
            email,
            password
        );
        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/v1/auth/login");

        String url = "/v1/auth/logout";

        // when
        ExtractableResponse<Response> result = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(url)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertTrue(result.cookie("accessToken").isEmpty());
        assertTrue(result.cookie("refreshToken").isEmpty());
    }
}
