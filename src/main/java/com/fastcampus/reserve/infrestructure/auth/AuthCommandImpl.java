package com.fastcampus.reserve.infrestructure.auth;

import com.fastcampus.reserve.domain.auth.Token;
import com.fastcampus.reserve.domain.auth.AuthCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthCommandImpl implements AuthCommand {

    private final TokenRedisRepository tokenRedisRepository;

    @Override
    public void store(Token token) {
        tokenRedisRepository.save(token);
    }
}
