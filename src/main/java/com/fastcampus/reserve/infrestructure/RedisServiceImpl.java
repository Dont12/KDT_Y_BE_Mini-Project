package com.fastcampus.reserve.infrestructure;

import static com.fastcampus.reserve.common.response.ErrorCode.COMMON_SYSTEM_ERROR;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        String serializedValue = redisTemplate.opsForValue().get(key);
        try {
            return Optional.of(objectMapper.readValue(serializedValue, type));
        } catch (Exception e) {
            throw new CustomException(COMMON_SYSTEM_ERROR);
        }
    }

    @Override
    public void set(String key, Object value, Long expiredTime) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, serializedValue, expiredTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new CustomException(COMMON_SYSTEM_ERROR);
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value, Long expiredTime) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            return Boolean.TRUE.equals(
                    redisTemplate.opsForValue().setIfAbsent(
                            key,
                            serializedValue,
                            expiredTime, TimeUnit.SECONDS
                    ));
        } catch (Exception e) {
            throw new CustomException(COMMON_SYSTEM_ERROR);
        }
    }

    @Override
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
