package com.fastcampus.reserve.domain.order;

import static com.fastcampus.reserve.common.response.ErrorCode.NOT_EXIST_REGISTER_ORDER;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.RedisService;
import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
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
    private final OrderCommand orderCommand;
    private final RegisterOrderFactory registerOrderFactory;

    public String registerOrder(RegisterOrderDto dto) {
        var registerOrder = registerOrderFactory.create(dto);
        var orderToken = UUID.randomUUID().toString();
        redisService.set(orderToken, registerOrder, INIT_ORDER_EXPIRED_TIME);
        return orderToken;
    }

    @Transactional
    public Long payment(PaymentDto dto) {
        var registerOrder = redisService.get(dto.orderToken(), RegisterOrder.class)
                .orElseThrow(() -> new CustomException(NOT_EXIST_REGISTER_ORDER));
        return orderCommand.payment(registerOrder, dto);
    }
}
