package com.fastcampus.reserve.application.order;

import com.fastcampus.reserve.domain.order.OrderService;
import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
import com.fastcampus.reserve.domain.order.dto.request.RegisterOrderDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderHistoriesDto;
import com.fastcampus.reserve.domain.order.dto.response.OrderInfoDto;
import com.fastcampus.reserve.domain.order.dto.response.RegisterOrderInfoDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    public String registerOrder(RegisterOrderDto dto) {
        return orderService.registerOrder(dto);
    }

    public Long payment(PaymentDto dto) {
        return orderService.payment(dto);
    }

    public RegisterOrderInfoDto findRegisterOrder(String orderToken) {
        return orderService.findRegisterOrder(orderToken);
    }

    public List<OrderHistoriesDto> findOrderHistories(Pageable pageable) {
        return orderService.findOrderHistories(pageable);
    }

    public OrderInfoDto findOrder(Long orderId) {
        return orderService.findOrder(orderId);
    }
}
