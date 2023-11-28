package com.fastcampus.reserve.restdocs.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import com.fastcampus.reserve.common.RestAssuredUtils;
import com.fastcampus.reserve.common.SecurityApiDocumentation;
import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.infrestructure.user.CartRepository;
import com.fastcampus.reserve.infrestructure.user.UserRepository;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class CartDocumentationTest extends ApiDocumentation {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void addItem() throws Exception {
        // given
        String email = "a@a.com";
        String password = "password";
        signup(email, password);
        Cookie cookie = login(email, password);

        CartItemAddRequest request = createCartItemAddRequest();
        byte[] param = objectMapper.writeValueAsBytes(request);

        // when
        this.mockMvc.perform(
                post("/v1/carts")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(param)
                    .cookie(cookie)
            )
            .andExpect(status().isOk())
            .andDo(document(
                "carts/addItem/success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("roomId").type(NUMBER)
                        .description("방의 고유번호"),
                    fieldWithPath("checkInDate").type(STRING)
                        .description("체크인 날짜").type(getDateFormat()),
                    fieldWithPath("checkOutDate").type(STRING)
                        .description("체크아웃 날짜").type(getDateFormat()),
                    fieldWithPath("guestCount").type(NUMBER)
                        .description("투숙할 인원")
                ),
                responseFields(
                    fieldWithPath("status").ignored()
                )
            ));
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
    void deleteItems() throws Exception {
        // given
        String email = "a@a.com";
        String password = "password";
        signup(email, password);

        Cookie cookie = login(email, password);

        addCartItem(cookie);
        addCartItem(cookie);

        List<Long> cartIds = cartRepository.findAll().stream()
            .map(Cart::getId).toList();
        System.out.println("@@@" + cartIds);

        CartItemDeleteRequest request = new CartItemDeleteRequest(
            cartIds
        );
        byte[] param = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(
                delete("/v1/carts")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(param)
                    .cookie(cookie)
            )
            .andExpect(status().isOk())
            .andDo(document(
                "carts/deleteItems/success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("cartIds").type(ARRAY)
                        .description("cartId의 배열")
                ),
                responseFields(
                    fieldWithPath("status").ignored()
                )
            ));
    }

    private void addCartItem(Cookie cookie) throws Exception {
        CartItemAddRequest request = new CartItemAddRequest(
            1L,
            LocalDate.of(2010, 1, 1),
            LocalDate.of(2010, 1, 2),
            4
        );
        byte[] param = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(
            post("/v1/carts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(param)
        ).andExpect(status().isOk());
    }

    private Cookie login(String email, String password) throws Exception {
        LoginRequest request = new LoginRequest(email, password);
        byte[] param = objectMapper.writeValueAsBytes(request);

        MvcResult mvcResult = this.mockMvc.perform(
            post("/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
        ).andExpect(status().isOk()).andReturn();
        return mvcResult.getResponse().getCookie("accessToken");
    }

    private void signup(String email, String password) throws Exception {
        SignupRequest request = new SignupRequest(
            email,
            password,
            "nickname",
            "010-0000-0000"
        );
        byte[] param = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(
            post("/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
        ).andExpect(status().isOk());
    }
}
