package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.OrderReader;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReaderImpl implements OrderReader {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Page<Order> findAllWithOrderItem(Pageable pageable) {
        return orderRepository.findAllWithOrderItem(pageable);
    }

    @Override
    public Order findByIdWithOrderItem(Long orderId) {
        return orderRepository.findByIdWithOrderItem(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ORDER));
    }

    @Override
    public int calcOrderedStock(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return orderItemRepository.countByTimeAndRoomId(
            roomId, checkInDate, checkOutDate
        );
    }
}
