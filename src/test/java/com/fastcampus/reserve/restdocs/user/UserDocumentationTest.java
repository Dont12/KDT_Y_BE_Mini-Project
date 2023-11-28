package com.fastcampus.reserve.restdocs.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class UserDocumentationTest extends ApiDocumentation {

    @Test
    void signup() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("email", "c@a.com");
        request.put("password", "password");
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
                "signup/success",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("status").ignored()
                )
            ));
    }

    @Test
    void myInfo() throws Exception {
        // given
        mockSecuritySetting();

        // when, then
        this.mockMvc.perform(
                get("/v1/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .cookie(new Cookie("accessToken", "mocked"))
            )
            .andExpect(status().isOk())
            .andDo(document(
                "myInfo/success",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("status").ignored(),
                    fieldWithPath("data.email").type(STRING)
                        .description("이메일"),
                    fieldWithPath("data.nickname").type(STRING)
                        .description("닉네임"),
                    fieldWithPath("data.phone").type(STRING)
                        .description("핸드폰 번호")
                )
            ));
    }
}
