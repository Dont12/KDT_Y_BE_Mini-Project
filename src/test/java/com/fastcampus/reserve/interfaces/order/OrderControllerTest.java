package com.fastcampus.reserve.interfaces.order;

import static com.fastcampus.reserve.common.CreateUtils.createProduct;
import static com.fastcampus.reserve.common.CreateUtils.createProductImage;
import static com.fastcampus.reserve.common.CreateUtils.createRoom;
import static com.fastcampus.reserve.common.CreateUtils.createRoomImage;
import static com.fastcampus.reserve.common.RestAssuredUtils.login;
import static com.fastcampus.reserve.domain.order.payment.Payment.CARD;
import static com.fastcampus.reserve.domain.order.payment.Payment.CASH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.dto.response.OrderItemInfoDto;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.infrestructure.order.OrderRepository;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.interfaces.order.dto.request.PaymentRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderItemRequest;
import com.fastcampus.reserve.interfaces.order.dto.request.RegisterOrderRequest;
import com.fastcampus.reserve.interfaces.order.dto.response.OrderHistoriesResponse;
import com.fastcampus.reserve.interfaces.order.dto.response.RegisterOrderItemInfoResponse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("주문 통합 테스트")
class OrderControllerTest extends ApiTest {

    private static Long roomId;
    private static Long productId;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        roomId = -1L;

        Product product = createProduct();
        ProductImage productImage = createProductImage();
        productImage.registerProduct(product);

        RoomImage roomImage = createRoomImage();
        Room room = createRoom();
        ReflectionTestUtils.setField(room, "id", roomId);
        roomImage.registerRoom(room);

        room.registerProduct(product);

        productId = productRepository.save(product).getId();
    }

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
    @DisplayName("예약 결제 동시성 테스트")
    void paymentFail() throws InterruptedException, ExecutionException {
        // given
        int numberOfThreads = 3;

        String url = "/v1/orders/payment";

        String accessToken = login();
        List<String> list = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> getOrderToken())
                .toList();

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        List<Future<ExtractableResponse<Response>>> futures = new ArrayList<>();

        // when
        IntStream.range(0, numberOfThreads)
                .forEach(i -> {
                    Future<ExtractableResponse<Response>> future = executorService.submit(() -> {
                        try {
                            PaymentRequest request = createPaymentRequest(list.get(i));
                            return RestAssured
                                    .given().log().all()
                                    .cookie("accessToken", accessToken)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .body(request)
                                    .when()
                                    .post(url)
                                    .then().log().all()
                                    .extract();
                        } finally {
                            latch.countDown();
                        }
                    });
                    futures.add(future);
                });
        latch.await();

        // then
        int successCount = 0;
        for (Future<ExtractableResponse<Response>> future : futures) {
            ExtractableResponse<Response> result = future.get();
            if (result.statusCode() == HttpStatus.OK.value()) {
                successCount++;
            }
        }

        assertThat(successCount).isEqualTo(2);
    }

    @Test
    @DisplayName("예약 신청 조회")
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
                        .isEqualTo(productId),
                () -> assertThat(response.productName())
                        .isEqualTo("productName"),
                () -> assertThat(response.imageUrl())
                        .isEqualTo("https://www.image.co.kr"),
                () -> assertThat(response.roomId())
                        .isEqualTo(roomId),
                () -> assertThat(response.roomName())
                        .isEqualTo("roomName"),
                () -> assertThat(response.guestCount())
                        .isEqualTo(4),
                () -> assertThat(response.maxGuestCount())
                        .isEqualTo(4),
                () -> assertThat(response.baseGuestCount())
                        .isEqualTo(2),
                () -> assertThat(response.price())
                        .isEqualTo(99000),
                () -> assertThat(response.day())
                        .isEqualTo(1L),
                () -> assertThat(response.checkInTime())
                        .isEqualTo("15:00"),
                () -> assertThat(response.checkInDate())
                        .isEqualTo(LocalDate.of(2023, 11, 28)),
                () -> assertThat(response.checkOutTime())
                        .isEqualTo("12:00"),
                () -> assertThat(response.checkOutDate())
                        .isEqualTo(LocalDate.of(2023, 11, 29))
        );
    }

    @Test
    @DisplayName("주문 내역 조회")
    void getOrderHistories() {
        // given
        IntStream.range(0, 2)
                .forEach(i -> getOrderId());
        String url = "/v1/orders/history";

        Order order = Order.builder()
                .userId(-99L)
                .reserveName("reserveName")
                .reservePhone("010-0000-0000")
                .userName("userName")
                .userPhone("010-7289-2911")
                .payment(CASH)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .productId(-99L)
                .productName("productName")
                .roomId(-99L)
                .roomName("roomName")
                .imageUrl("imageUrl")
                .guestCount(2)
                .price(129000)
                .baseGuestCount(2)
                .maxGuestCount(4)
                .checkInDate(LocalDate.now())
                .checkInTime("15:30")
                .checkOutDate(LocalDate.now().plusDays(1))
                .checkOutTime("11:00")
                .build();
        orderItem.registerOrder(order);
        orderRepository.save(order);

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.getWithLogin(url);

        // then
        JsonPath jsonPath = result.jsonPath();
        OrderHistoriesResponse response = jsonPath.getObject(
                "data",
                OrderHistoriesResponse.class
        );

        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.size()).isEqualTo(10),
                () -> assertThat(response.pageNumber()).isEqualTo(1),
                () -> assertThat(response.totalPages()).isEqualTo(1),
                () -> assertThat(response.totalElements()).isEqualTo(2)
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
                        .isEqualTo(LocalDate.now().toString()),
                () -> assertThat(jsonPath.getString("data.payment"))
                        .isEqualTo("CARD"),
                () -> assertThat(response.orderItemId())
                        .isEqualTo(1L),
                () -> assertThat(response.productId())
                        .isEqualTo(productId),
                () -> assertThat(response.productName())
                        .isEqualTo("productName"),
                () -> assertThat(response.roomName())
                        .isEqualTo("roomName"),
                () -> assertThat(response.imageUrl())
                        .isEqualTo("https://www.image.co.kr"),
                () -> assertThat(response.maxGuestCount())
                        .isEqualTo(4),
                () -> assertThat(response.baseGuestCount())
                        .isEqualTo(2),
                () -> assertThat(response.day())
                        .isEqualTo(1L),
                () -> assertThat(response.checkInTime())
                        .isEqualTo("15:00"),
                () -> assertThat(response.checkInDate())
                        .isEqualTo(LocalDate.of(2023, 11, 28)),
                () -> assertThat(response.checkOutTime())
                        .isEqualTo("12:00"),
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
                productId,
                roomId,
                LocalDate.of(2023, 11, 28),
                "15:00",
                LocalDate.of(2023, 11, 29),
                "12:00",
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
