package com.fastcampus.reserve.common;

import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public final class RestAssuredUtils {

    public static final String TOKEN_COOKIE_NAME = "accessToken";
    public static final String EMAIL = "user@gmail.com";
    public static final String PASSWORD = "password";

    private RestAssuredUtils() {
    }

    public static ExtractableResponse<Response> postWithLogin(String url, Object param) {
        String accessToken = login();
        return RestAssured
                .given().log().all()
                .cookie(TOKEN_COOKIE_NAME, accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(param)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public static void signup() {
        SignupRequest request = new SignupRequest(
                EMAIL,
                PASSWORD,
                "nickname",
                "010-0000-0000"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/v1/users")
                .then().log().all()
                .extract();
    }

    private static String login() {
        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/v1/auth/login")
                .then().log().all()
                .extract();
        return response.cookie(TOKEN_COOKIE_NAME);
    }
}
