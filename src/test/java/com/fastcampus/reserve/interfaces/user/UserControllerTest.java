package com.fastcampus.reserve.interfaces.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.infrestructure.user.UserRepository;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class UserControllerTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;

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
        ExtractableResponse<Response> result = RestAssuredUtils.getWithLogin(
            "/v1/users"
        );

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
