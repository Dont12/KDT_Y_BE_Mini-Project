package com.fastcampus.reserve.domain.order;

import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private static final long INIT_ORDER_EXPIRED_TIME = 3600L;

    private final RedisService redisService;
    private final RegisterOrderFactory registerOrderFactory;

    public String registerOrder(RegisterOrderDto dto) {
        var registerOrder = registerOrderFactory.create(dto);
        var orderToken = UUID.randomUUID().toString();
        redisService.set(orderToken, registerOrder, INIT_ORDER_EXPIRED_TIME);
        return orderToken;
    }
}
