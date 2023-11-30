package com.fastcampus.reserve.common.security.config;

import com.fastcampus.reserve.common.security.config.handler.TokenAccessDeniedHandler;
import com.fastcampus.reserve.common.security.config.handler.TokenAuthenticationEntryPoint;
import com.fastcampus.reserve.common.security.jwt.JwtProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String FRONT_URL_LOCAL = "http://localhost:3000";
    private final JwtProvider jwtProvider;

    private static final String[] WHITELIST_URLS = {
        "/v1/users", "/v1/auth/login", "/v1/auth/logout",
        "/v1/products", "/v1/products/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer
                    .configurationSource(corsConfigurationSource())
            )
            .headers(header -> header.frameOptions(FrameOptionsConfig::disable).disable())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    Arrays.stream(WHITELIST_URLS)
                        .map(AntPathRequestMatcher::new)
                        .toArray(AntPathRequestMatcher[]::new)
                ).permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated())
        ;

        http
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(new TokenAccessDeniedHandler())
                .authenticationEntryPoint(new TokenAuthenticationEntryPoint())
            )
            .apply(new SecurityConfigAdapter(jwtProvider));

        return http.getOrBuild();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern(FRONT_URL_LOCAL);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
