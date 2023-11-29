package com.fastcampus.reserve.restdocs.order;

import static com.fastcampus.reserve.common.CreateUtils.createOrder;
import static com.fastcampus.reserve.common.CreateUtils.createOrderItem;
import static com.fastcampus.reserve.common.CreateUtils.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import com.fastcampus.reserve.common.SecurityApiDocumentation;
import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import com.fastcampus.reserve.domain.user.UserReader;
import com.fastcampus.reserve.infrestructure.order.OrderRepository;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

public class OrderDocumentationTest extends SecurityApiDocumentation {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private RedisService redisService;
    @MockBean
    private RoomReader roomReader;

    @BeforeEach
    void setUp() {
        RoomImage roomImage = RoomImage.builder()
            .url("https://www.image.co.kr")
            .build();

        Room room = Room.builder()
            .name("name")
            .price(99000)
            .stock(12)
            .checkInTime("15:00")
            .checkOutTime("12:00")
            .baseGuestCount(2)
            .maxGuestCount(4)
            .build();

        room.addImage(roomImage);

        ReflectionTestUtils.setField(room, "id", -1L);

        when(roomReader.findByIdWithImage(anyLong())).thenReturn(room);
    }

    @Test
    void registerOrder() throws Exception {
        mockSecuritySetting();
        when(userReader.findById(anyLong())).thenReturn(createUser());

        Map<String, Object> registerOrder = createRegisterOrder();
        byte[] param = objectMapper.writeValueAsBytes(registerOrder);

        this.mockMvc.perform(
                        post("/v1/orders")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("accessToken", "accessToken"))
                                .content(param)
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "order/registerOrder/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("registerOrderItems").type(ARRAY)
                                        .description("예약 신청 정보 목록").optional(),
                                fieldWithPath("registerOrderItems[].productId").type(NUMBER)
                                        .description("숙박 업소 ID"),
                                fieldWithPath("registerOrderItems[].roomId").type(NUMBER)
                                        .description("방 ID"),
                                fieldWithPath("registerOrderItems[].checkInDate").type(STRING)
                                        .attributes(getDateFormat())
                                        .description("체크인 날짜"),
                                fieldWithPath("registerOrderItems[].checkInTime").type(STRING)
                                        .attributes(getTimeFormat())
                                        .description("체크인 시간"),
                                fieldWithPath("registerOrderItems[].checkOutDate").type(STRING)
                                        .attributes(getDateFormat())
                                        .description("체크아웃 날짜"),
                                fieldWithPath("registerOrderItems[].checkOutTime").type(STRING)
                                        .attributes(getTimeFormat())
                                        .description("체크아웃 시간"),
                                fieldWithPath("registerOrderItems[].guestCount").type(NUMBER)
                                        .description("예약 인원"),
                                fieldWithPath("registerOrderItems[].price").type(NUMBER)
                                        .description("예약 가격")
                        ),
                        responseFields(
                                fieldWithPath("status").ignored(),
                                fieldWithPath("data.orderToken").type(STRING)
                                        .description("임시 예약 번호")
                        )
                ));
    }

    @Test
    void payment() throws Exception {
        Order order = createOrder();
        ReflectionTestUtils.setField(order, "id", -1L);

        OrderItem orderItem = createOrderItem();

        RegisterOrder registerOrder = RegisterOrder.builder()
                .userId(-1L)
                .name("userName")
                .phone("010-0000-0000")
                .orderItems(List.of(orderItem))
                .build();

        mockSecuritySetting();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(redisService.get(anyString(), any())).thenReturn(Optional.of(registerOrder));

        Map<String, Object> payment = createPayment();
        byte[] param = objectMapper.writeValueAsBytes(payment);

        this.mockMvc.perform(
                        post("/v1/orders/payment")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("accessToken", "accessToken"))
                                .content(param)
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "order/payment/success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("orderToken").type(STRING)
                                        .description("임시 예약 신청 번호"),
                                fieldWithPath("userName").type(STRING)
                                        .description("이용자 이름"),
                                fieldWithPath("userPhone").type(STRING)
                                        .attributes(getPhoneFormat())
                                        .description("이용자 휴대폰 번호"),
                                fieldWithPath("price").type(NUMBER)
                                        .description("예약 가격"),
                                fieldWithPath("payment").type(STRING)
                                        .description("결제 수단")
                        ),
                        responseFields(
                                fieldWithPath("status").ignored(),
                                fieldWithPath("data.orderId").type(NUMBER)
                                        .description("예약 번호")
                        )
                ));
    }

    private Map<String, Object> createRegisterOrder() {
        Map<String, Object> registerOrderItems = createRegisterOrderItems();
        Map<String, Object> registerOrder = new HashMap<>();
        registerOrder.put("registerOrderItems", List.of(registerOrderItems));
        return registerOrder;
    }

    private Map<String, Object> createRegisterOrderItems() {
        Map<String, Object> registerOrderItems = new HashMap<>();
        registerOrderItems.put("productId", -1L);
        registerOrderItems.put("roomId", -1L);
        registerOrderItems.put("checkInDate", "2023-11-23");
        registerOrderItems.put("checkInTime", "15:00");
        registerOrderItems.put("checkOutDate", "2023-11-25");
        registerOrderItems.put("checkOutTime", "12:00");
        registerOrderItems.put("guestCount", 4);
        registerOrderItems.put("price", 99000);
        return registerOrderItems;
    }

    private Map<String, Object> createPayment() {
        Map<String, Object> payment = new HashMap<>();
        payment.put("orderToken", "58f795f6-2795-4d19-9dba-c1032209c05b");
        payment.put("userName", "userName");
        payment.put("userPhone", "userPhone");
        payment.put("price", 99000);
        payment.put("payment", "CARD");
        return payment;
    }
}
