package com.fastcampus.reserve.restdocs.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.SecurityApiDocumentation;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class CartDocumentationTest extends SecurityApiDocumentation {

    @Test
    void addItem() throws Exception {
        // given
        CartItemAddRequest request = createCartItemAddRequest();
        byte[] param = objectMapper.writeValueAsBytes(request);
        mockSecuritySetting();

        // when
        this.mockMvc.perform(
                post("/v1/carts")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(param)
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
}
