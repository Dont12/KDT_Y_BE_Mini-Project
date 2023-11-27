package com.fastcampus.reserve.interfaces.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends ApiTest {

    @Test
    void registerOrder() {
        // given
        RegisterOrderItemRequest registerOrderItemRequest = new RegisterOrderItemRequest(
                -1L,
                -1L,
                LocalDate.now(),
                LocalTime.of(15, 0),
                LocalDate.now().plusDays(1),
                LocalTime.of(12, 0),
                4,
                99000
        );

        RegisterOrderRequest request = new RegisterOrderRequest(List.of(registerOrderItemRequest));

        String url = "/v1/orders";

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
