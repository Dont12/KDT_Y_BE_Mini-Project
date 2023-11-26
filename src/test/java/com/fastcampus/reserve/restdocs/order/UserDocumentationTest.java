package com.fastcampus.reserve.restdocs.order;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class UserDocumentationTest extends ApiDocumentation {

    @Test
    void signup() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("email", "a@a.com");
        request.put("password", "a@a.com");
        request.put("nickname", "nickname");
        request.put("phone", "010-0000-0000");

        byte[] param = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(
            post("/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
        )
        .andExpect(status().isOk())
        .andDo(document(
            "users/success",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("email").type(STRING)
                    .description("이메일"),
                fieldWithPath("password").type(STRING)
                    .description("비밀번호 (6자 이상)"),
                fieldWithPath("nickname").type(STRING)
                    .description("닉네임"),
                fieldWithPath("phone").type(STRING)
                    .description("핸드폰 번호 (010-0000-0000 형식)")
            ),
            responseFields(
                fieldWithPath("status").ignored()
            )
        ));
    }
}
