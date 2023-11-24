package com.fastcampus.reserve.restdocs.order;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class OrderDocumentationTest extends ApiDocumentation {

    @Test
    void registerOrder() throws Exception {
        Map<String, Object> registerOrderItems = new HashMap<>();
        registerOrderItems.put("productId", -1L);
        registerOrderItems.put("roomId", -1L);
        registerOrderItems.put("checkInDate", "2023-11-23");
        registerOrderItems.put("checkInTime", "15:00");
        registerOrderItems.put("checkOutDate", "2023-11-25");
        registerOrderItems.put("checkOutTime", "12:00");
        registerOrderItems.put("guestCount", 4);
        registerOrderItems.put("price", 99000);

        Map<String, Object> registerOrder = new HashMap<>();
        registerOrder.put("registerOrderItems", List.of(registerOrderItems));

        byte[] param = objectMapper.writeValueAsBytes(registerOrder);

        this.mockMvc.perform(
                post("/v1/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
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
}
