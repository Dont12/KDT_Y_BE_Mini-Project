package com.fastcampus.reserve.restdocs.user;

import static com.fastcampus.reserve.common.CreateUtils.createProductImage;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.domain.user.Cart;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.infrestructure.product.room.RoomRepository;
import com.fastcampus.reserve.infrestructure.user.CartRepository;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartOrderRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class CartDocumentationTest extends ApiDocumentation {

    @Autowired
    private CartRepository cartRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private RoomRepository roomRepository;

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

    @Test
    void deleteItems() throws Exception {
        // given
        String email = "b@a.com";
        String password = "password";
        signup(email, password);

        Cookie cookie = login(email, password);

        cartRepository.deleteAll();
        addCartItem(cookie);
        addCartItem(cookie);

        List<Long> cartIds = cartRepository.findAll().stream()
            .map(Cart::getId).toList();

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

    @Test
    void getList() throws Exception {
        // given
        String email = "c@a.com";
        String password = "password";
        signup(email, password);

        Cookie cookie = login(email, password);

        cartRepository.deleteAll();
        addCartItem(cookie);
        addCartItem(cookie);

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

        this.mockMvc.perform(
                get("/v1/carts")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
            )
            .andExpect(status().isOk())
            .andDo(document(
                "carts/getList/success",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("status").ignored(),
                    fieldWithPath("data.page.size").type(NUMBER)
                        .description("items의 개수"),
                    fieldWithPath("data.page.maxPage").type(NUMBER)
                        .description("마지막 페이지 번호"),
                    fieldWithPath("data.page.totalCount").type(NUMBER)
                        .description("모든 페이지 통틀어 item의 총 개수"),
                    fieldWithPath("data.totalPrice").type(NUMBER)
                        .description("총 가격"),
                    fieldWithPath("data.items[].id").type(NUMBER)
                        .description("장바구니 id"),
                    fieldWithPath("data.items[].product.productId").type(NUMBER)
                        .description("숙박지 id"),
                    fieldWithPath("data.items[].product.productName").type(STRING)
                        .description("숙박지 이름"),
                    fieldWithPath("data.items[].product.roomId").type(NUMBER)
                        .description("방 id"),
                    fieldWithPath("data.items[].product.imageUrl").type(STRING)
                        .description("상품 이미지 URL"),
                    fieldWithPath("data.items[].product.address").type(STRING)
                        .description("숙박지 주소"),
                    fieldWithPath("data.items[].product.roomName").type(STRING)
                        .description("방 이름"),
                    fieldWithPath("data.items[].product.baseGuestCount").type(NUMBER)
                        .description("기준 투숙객 수"),
                    fieldWithPath("data.items[].product.maxGuestCount").type(NUMBER)
                        .description("최대 투숙객 수"),
                    fieldWithPath("data.items[].product.price").type(NUMBER)
                        .description("방 가격"),
                    fieldWithPath("data.items[].product.checkInTime").type(STRING)
                        .description("체크인 시간"),
                    fieldWithPath("data.items[].product.checkOutTime").type(STRING)
                        .description("체크아웃 시간"),
                    fieldWithPath("data.items[].product.stock").type(NUMBER)
                        .description("재고 수량"),
                    fieldWithPath("data.items[].checkInDate").type(STRING)
                        .description("체크인 날짜"),
                    fieldWithPath("data.items[].checkOutDate").type(STRING)
                        .description("체크아웃 날짜"),
                    fieldWithPath("data.items[].numberOfNights").type(NUMBER)
                        .description("숙박 일수"),
                    fieldWithPath("data.items[].guestCount").type(NUMBER)
                        .description("게스트 수")
                )
                )
            );
    }

    @Test
    void order() throws Exception {
        // given
        String email = "d@a.com";
        String password = "password";
        signup(email, password);

        Cookie cookie = login(email, password);

        cartRepository.deleteAll();
        addCartItem(cookie);
        addCartItem(cookie);

        Product product = Product.builder().id(1L).description("description")
            .address("address").area("area").category("category")
            .latitude("0.0").longitude("0.0").sigungu("sigungu")
            .zipCode("00000").name("name").build();
        ProductImage productImage = createProductImage();
        productImage.registerProduct(product);
        Room room = Room.builder().id(1L).product(product).stock(3)
            .price(10000).checkInTime("11:11").checkOutTime("12:12")
            .maxGuestCount(4).baseGuestCount(2).roomFacilities("abcabc")
            .name("name").build();
        room.addImage(RoomImage.builder().url("url").build());
        when(productRepository.findByIdWithImage(anyLong())).thenReturn(Optional.of(product));
        when(roomRepository.findByIdWithImage(1L)).thenReturn(Optional.of(room));

        List<Cart> cartItems = cartRepository.findAll();
        CartOrderRequest request = new CartOrderRequest(
            cartItems.stream().map(Cart::getId).toList()
        );

        byte[] param = objectMapper.writeValueAsBytes(request);

        // when, then
        this.mockMvc.perform(
                post("/v1/carts/order")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
                    .content(param)
            )
            .andExpect(status().isOk())
            .andDo(document(
                    "carts/order/success",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("cartIds").type(ARRAY)
                            .description("주문할 아이템 id 목록")
                    ),
                    responseFields(
                        fieldWithPath("status").ignored(),
                        fieldWithPath("data.orderToken").type(STRING)
                            .description("임시 예약 번호")
                    )
                )
            );
    }

    private CartItemAddRequest createCartItemAddRequest() {
        return new CartItemAddRequest(
            1L,
            LocalDate.of(2010, 1, 1),
            LocalDate.of(2010, 1, 2),
            4
        );
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
