package com.fastcampus.reserve.interfaces.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.reserve.common.ApiTest;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.infrestructure.user.CartRepository;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class CartControllerTest extends ApiTest {

    @Autowired
    private CartRepository cartRepository;

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
}
