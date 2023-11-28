package com.fastcampus.reserve.common;

import static com.fastcampus.reserve.common.CreateUtils.createUser;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import com.fastcampus.reserve.domain.user.UserReader;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class SecurityApiDocumentation extends ApiDocumentation {

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected UserReader userReader;

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
}
