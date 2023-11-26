package com.fastcampus.reserve.common.security.jwt;

import static com.fastcampus.reserve.common.response.ErrorCode.NOT_AUTHORITY_TOKEN;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.domain.auth.Token;
import com.fastcampus.reserve.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtProvider {

    private static final int MILLISECONDS_TO_SECONDS = 1000;
    private static final int TOKEN_REFRESH_INTERVAL = MILLISECONDS_TO_SECONDS * 24;
    private static final String AUTHORITIES_KEY = "role";
    private static final String SEPARATE = ",";

    private final Key key;
    private final String grantType;
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;

    public JwtProvider(
            @Value("${security.jwt.secret}") String secretKey,
            @Value("${security.jwt.grand-type}") String grantType,
            @Value("${security.jwt.token-validate-in-seconds}") long tokenValidateSeconds
    ) {
        this.key = getSecretKey(secretKey);
        this.grantType = grantType;
        this.accessTokenExpiredTime = tokenValidateSeconds * MILLISECONDS_TO_SECONDS;
        this.refreshTokenExpiredTime = tokenValidateSeconds * TOKEN_REFRESH_INTERVAL;
    }

    public Token generateToken(User user) {
        return Token.builder()
                .grantType(grantType)
                .refreshToken(createToken(user, accessTokenExpiredTime))
                .accessToken(createToken(user, refreshTokenExpiredTime))
                .build();
    }

    public String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(grantType)) {
            return token.substring(grantType.length() + 1);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new CustomException(NOT_AUTHORITY_TOKEN);
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = getGrantedAuthorities(claims);
        PrincipalDetails principal = new PrincipalDetails(claims.getSubject(), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 입니다.");
        }
        return false;
    }

    private SecretKey getSecretKey(String secretKey) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(User user, long expiredTime) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(key)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", SignatureAlgorithm.HS256.getValue());
        header.put("typ", Elements.JWT);
        return header;
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        // 원래 getAuthorityClaim(user) 통해 받아와야 하나
        // Authority 저장이 제대로 되지않는 이슈 해결이 어려워 임시로 고정값으로 해둠
        // TODO
        claims.put(AUTHORITIES_KEY, User.RoleType.USER);
        return claims;
    }

    private String getAuthorityClaim(User user) {
        return user.getAuthorities().stream()
                .map(authority -> authority.getRole().name())
                .collect(Collectors.joining(SEPARATE));
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Claims claims) {
        return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
