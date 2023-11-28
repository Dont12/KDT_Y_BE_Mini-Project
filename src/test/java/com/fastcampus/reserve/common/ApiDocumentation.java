package com.fastcampus.reserve.common;

import static com.fastcampus.reserve.common.CreateUtils.createUser;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import com.fastcampus.reserve.domain.user.UserReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public abstract class ApiDocumentation {

    private static final String BASE_URL = "localhost";

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserReader userReader;

    @Autowired
    protected MockMvc mockMvc;

    protected OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .host(BASE_URL)
                        .removePort(),
                prettyPrint());
    }

    protected OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    protected void mockSecuritySetting() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        PrincipalDetails principal = new PrincipalDetails(1L, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                "",
                authorities
        );

        when(userReader.findById(anyLong())).thenReturn(createUser());
        when(jwtProvider.validate(anyString())).thenReturn(true);
        when(jwtProvider.resolveToken(anyString())).thenReturn("token");
        when(jwtProvider.getAuthentication(anyString())).thenReturn(authentication);
    }

    protected Attribute getTimeFormat() {
        return key("format").value("hh:mm");
    }

    protected Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }

    protected Attribute getPhoneFormat() {
        return key("format").value("000-0000-0000");
    }

    protected Attribute getEmailFormat() {
        return key("format").value("user@domain.com");
    }
}
