package com.fastcampus.reserve.interfaces.order;

import static com.fastcampus.reserve.domain.order.payment.Payment.CARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.domain.order.dto.response.OrderItemInfoDto;
import com.fastcampus.reserve.interfaces.order.dto.request.PaymentRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderItemInfoResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("주문 통합 테스트")
class OrderControllerTest extends ApiTest {

    @Test
    @DisplayName("예약 신청")
    void registerOrder() {
        // given
        RegisterOrderRequest request = createRequestOrderRequest();

        String url = "/v1/orders";

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 결제")
    void payment() {
        // given
        String orderToken = getOrderToken();
        PaymentRequest request = createPaymentRequest(orderToken);

        String url = "/v1/orders/payment";

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 신청 조정")
    void getRegisterOrder() {
        // given
        String orderToken = getOrderToken();

        String url = "/v1/orders?orderToken=" + orderToken;

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.getWithLogin(url);

        // then
        JsonPath jsonPath = result.jsonPath();
        RegisterOrderItemInfoResponse response = jsonPath.getList(
                        "data.registerOrderItems",
                        RegisterOrderItemInfoResponse.class
                )
                .get(0);

        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getString("data.orderToken"))
                        .isEqualTo(orderToken),
                () -> assertThat(jsonPath.getInt("data.totalPrice"))
                        .isEqualTo(99000),
                () -> assertThat(jsonPath.getString("data.name"))
                        .isEqualTo("nickname"),
                () -> assertThat(jsonPath.getString("data.phone"))
                        .isEqualTo("010-0000-0000"),
                () -> assertThat(response.productId())
                        .isEqualTo(-1L),
                () -> assertThat(response.productName())
                        .isEqualTo("name"),
                () -> assertThat(response.imageUrl())
                        .isEqualTo("https://www.image.co.kr"),
                () -> assertThat(response.roomId())
                        .isEqualTo(-1L),
                () -> assertThat(response.roomName())
                        .isEqualTo("name"),
                () -> assertThat(response.guestCount())
                        .isEqualTo(4),
                () -> assertThat(response.maxGuestCount())
                        .isEqualTo(4),
                () -> assertThat(response.baseGuestCount())
                        .isEqualTo(2),
                () -> assertThat(response.price())
                        .isEqualTo(99000),
                () -> assertThat(response.checkInTime())
                        .isEqualTo(LocalTime.of(15, 0)),
                () -> assertThat(response.checkInDate())
                        .isEqualTo(LocalDate.of(2023, 11, 28)),
                () -> assertThat(response.checkOutTime())
                        .isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(response.checkOutDate())
                        .isEqualTo(LocalDate.of(2023, 11, 29))
        );
    }

    @Test
    @DisplayName("주문 내역 상세 조회")
    void getOrder() {
        // given
        Long orderId = getOrderId();
        String url = "/v1/orders/history/" + orderId;

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.getWithLogin(url);

        // then
        JsonPath jsonPath = result.jsonPath();
        OrderItemInfoDto response = jsonPath.getList(
                        "data.orderItems",
                        OrderItemInfoDto.class
                )
                .get(0);

        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getLong("data.orderId"))
                        .isEqualTo(orderId),
                () -> assertThat(jsonPath.getString("data.reserveName"))
                        .isEqualTo("nickname"),
                () -> assertThat(jsonPath.getString("data.reservePhone"))
                        .isEqualTo("010-0000-0000"),
                () -> assertThat(jsonPath.getString("data.userName"))
                        .isEqualTo("userName"),
                () -> assertThat(jsonPath.getString("data.userPhone"))
                        .isEqualTo("010-0000-0000"),
                () -> assertThat(jsonPath.getInt("data.totalPrice"))
                        .isEqualTo(99000),
                () -> assertThat(jsonPath.getString("data.reserveDate"))
                        .isEqualTo("2023-11-28"),
                () -> assertThat(jsonPath.getString("data.payment"))
                        .isEqualTo("CARD"),
                () -> assertThat(response.orderItemId())
                        .isEqualTo(1L),
                () -> assertThat(response.productId())
                        .isEqualTo(-1L),
                () -> assertThat(response.productName())
                        .isEqualTo("name"),
                () -> assertThat(response.roomName())
                        .isEqualTo("name"),
                () -> assertThat(response.imageUrl())
                        .isEqualTo("https://www.image.co.kr"),
                () -> assertThat(response.maxGuestCount())
                        .isEqualTo(4),
                () -> assertThat(response.baseGuestCount())
                        .isEqualTo(2),
                () -> assertThat(response.checkInTime())
                        .isEqualTo(LocalTime.of(15, 0)),
                () -> assertThat(response.checkInDate())
                        .isEqualTo(LocalDate.of(2023, 11, 28)),
                () -> assertThat(response.checkOutTime())
                        .isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(response.checkOutDate())
                        .isEqualTo(LocalDate.of(2023, 11, 29))
        );
    }

    private String getOrderToken() {
        return RestAssuredUtils
                .postWithLogin("/v1/orders", createRequestOrderRequest())
                .jsonPath()
                .getString("data.orderToken");
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

    private Long getOrderId() {
        String orderToken = getOrderToken();
        PaymentRequest request = createPaymentRequest(orderToken);

        return RestAssuredUtils.postWithLogin("/v1/orders/payment", request)
                .jsonPath()
                .getLong("data.orderId");
    }

    private PaymentRequest createPaymentRequest(String orderToken) {
        return new PaymentRequest(
                orderToken,
                "userName",
                "010-0000-0000",
                99000,
                CARD
        );
    }
}
