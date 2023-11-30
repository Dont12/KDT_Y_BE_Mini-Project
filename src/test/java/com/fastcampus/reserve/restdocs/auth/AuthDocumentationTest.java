package com.fastcampus.reserve.restdocs.auth;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.common.ApiDocumentation;
import com.fastcampus.reserve.domain.user.UserService;
import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class AuthDocumentationTest extends ApiDocumentation {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocument) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocument)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .build();


        MockitoAnnotations.openMocks(this);

    }

    @Test
    void login() throws Exception {
        String email = "f@a.com";
        String password = "password";
        userService.signup(new SignupDto(email, password, "nick", "010-0000-0000"));

        Map<String, Object> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);

        byte[] param = objectMapper.writeValueAsBytes(request);

        this.mockMvc.perform(
            post("/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
        )
            .andExpect(status().isOk())
            .andDo(document(
                "login/success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(STRING)
                        .description("이메일"),
                    fieldWithPath("password").type(STRING)
                        .description("비밀번호")
                ),
                responseFields(
                    fieldWithPath("status").ignored()
                )
            ));
    }

    @Test
    void logout() throws Exception {
        this.mockMvc.perform(
                post("/v1/auth/logout")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "logout/success",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("status").ignored()
                )
            ));
    }
}
