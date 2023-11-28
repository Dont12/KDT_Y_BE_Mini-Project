package com.fastcampus.reserve.interfaces.order;

import static com.fastcampus.reserve.domain.order.payment.Payment.CARD;
import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.interfaces.order.dto.request.PaymentRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends ApiTest {

    @Test
    void registerOrder() {
        // given
        RegisterOrderRequest request = createRequestOrderRequest();

        String url = "/v1/orders";

        // when
        ExtractableResponse<Response> response = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(response.statusCode())
                .withFailMessage(
                        "[%s] %s",
                        response.statusCode(),
                        response.jsonPath()
                )
                .isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 결제")
    void payment() {
        // given
        String orderToken = RestAssuredUtils
                .postWithLogin("/v1/orders", createRequestOrderRequest())
                .jsonPath()
                .getString("data.orderToken");

        PaymentRequest request = new PaymentRequest(
                orderToken,
                "userName",
                "010-0000-0000",
                99000,
                CARD
        );

        String url = "/v1/orders/payment";

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private RegisterOrderRequest createRequestOrderRequest() {
        return new RegisterOrderRequest(List.of(createRegisterOrderItemRequest()));
    }

    private RegisterOrderItemRequest createRegisterOrderItemRequest() {
        return new RegisterOrderItemRequest(
                -1L,
                -1L,
                LocalDate.now(),
                LocalTime.of(15, 0),
                LocalDate.now().plusDays(1),
                LocalTime.of(12, 0),
                4,
                99000
        );
    }
}
