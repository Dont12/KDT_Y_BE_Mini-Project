package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.OrderReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReaderImpl implements OrderReader {

    private final OrderRepository orderRepository;

    @Override
    public Order findByIdWithOrderItem(Long orderId) {
        return orderRepository.findByIdWithOrderItem(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ORDER));
    }
}
