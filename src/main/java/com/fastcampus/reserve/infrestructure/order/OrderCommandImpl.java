package com.fastcampus.reserve.infrestructure.order;

import static com.fastcampus.reserve.common.response.ErrorCode.NOT_MATCH_TOTAL_PRICE;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.OrderCommand;
import com.fastcampus.reserve.domain.order.RegisterOrder;
import com.fastcampus.reserve.domain.order.dto.request.PaymentDto;
import com.fastcampus.reserve.domain.user.CartCommand;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCommandImpl implements OrderCommand {

    private final OrderRepository orderRepository;
    private final CartCommand cartCommand;

    @Override
    public Long payment(RegisterOrder registerOrder, PaymentDto dto) {
        if (registerOrder.calcTotalPrice() != dto.price()) {
            throw new CustomException(NOT_MATCH_TOTAL_PRICE);
        }

        Order order = Order.builder()
                .userId(registerOrder.userId())
                .userName(dto.userName())
                .userPhone(dto.userPhone())
                .payment(dto.payment())
                .build();

        registerOrder.orderItems().forEach(orderItem -> {
            if (!Objects.isNull(orderItem.getCartId())) {
                cartCommand.deleteById(orderItem.getCartId());
            }
            orderItem.registerOrder(order);
        });

        orderRepository.save(order);
        return order.getId();
    }
}
