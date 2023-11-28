package com.fastcampus.reserve.interfaces.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.domain.user.User;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.infrestructure.product.room.RoomRepository;
import com.fastcampus.reserve.infrestructure.user.CartRepository;
import com.fastcampus.reserve.infrestructure.user.UserRepository;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CartControllerTest extends ApiTest {

    @Autowired
    private CartRepository cartRepository;
    @MockBean
    private RoomRepository roomRepository;

    @Test
    void addItem() {
        // given
        CartItemAddRequest request = createCartItemAddRequest();
        String url = "/v1/carts";

        // when
        ExtractableResponse<Response> result = RestAssuredUtils.postWithLogin(url, request);

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());

        var cart = cartRepository.findById(1L).orElseThrow();
        assertThat(cart.getRoomId()).isEqualTo(request.roomId());
        assertThat(cart.getUser().getId()).isEqualTo(1L);
        assertThat(cart.getGuestCount()).isEqualTo(request.guestCount());
        assertThat(cart.getCheckInDate()).isEqualTo(request.checkInDate());
        assertThat(cart.getCheckOutDate()).isEqualTo(request.checkOutDate());
    }

    private CartItemAddRequest createCartItemAddRequest() {
        return new CartItemAddRequest(
            1L,
            LocalDate.of(2010, 1, 1),
            LocalDate.of(2010, 1, 2),
            4
        );
    }

    @Test
    void deleteItems() {
        // given
        String email = "a@a.com";
        String password = "password";
        signup(email, password);

        String accessToken = login(email, password);

        addCartItem(accessToken);
        addCartItem(accessToken);

        List<Long> cartIds = cartRepository.findAll().stream()
            .map(Cart::getId).toList();

        CartItemDeleteRequest request = new CartItemDeleteRequest(
            cartIds
        );

        String url = "/v1/carts";

        // when
        ExtractableResponse<Response> result = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .cookie("accessToken", accessToken)
            .when()
            .delete(url)
            .then().log().all()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(cartRepository.count()).isEqualTo(0);
    }

    @Test
    void getList() {
        // given
        String email = "a@a.com";
        String password = "password";
        signup(email, password);

        String accessToken = login(email, password);

        addCartItem(accessToken);
        addCartItem(accessToken);

        Product product = Product.builder().id(10L).description("description")
                .address("address").area("area").category("category")
                .latitude("0.0").longitude("0.0").sigungu("sigungu")
                .zipCode("00000").name("name").build();
        Room room = Room.builder().id(1L).product(product).stock(3)
                .price(10000).checkInTime("11:11").checkOutTime("12:12")
                .maxGuestCount(4).baseGuestCount(2).roomFacilities("abcabc")
                .name("name").build();
        room.addImage(RoomImage.builder().url("url").build());
        when(roomRepository.findByIdWithImage(1L)).thenReturn(Optional.of(room));

        String url = "/v1/carts";

        // when
        ExtractableResponse<Response> result = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .cookie("accessToken", accessToken)
            .when()
            .get(url)
            .then().log().all()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.jsonPath().getList("data.items").size()).isEqualTo(2);
    }

    private void addCartItem(String accessToken) {
        CartItemAddRequest request = new CartItemAddRequest(
            1L,
            LocalDate.of(2010, 1, 1),
            LocalDate.of(2010, 1, 2),
            4
        );
        RestAssuredUtils.postWithCustomLogin("/v1/carts", request, accessToken);
    }

    private String login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);

        ExtractableResponse<Response> result =
            RestAssuredUtils.post("/v1/auth/login", request);
        return result.cookie("accessToken");
    }

    private void signup(String email, String password) {
        SignupRequest request = new SignupRequest(
            email,
            password,
            "nickname",
            "010-0000-0000"
        );
        RestAssuredUtils.post("/v1/users", request);
    }
}
