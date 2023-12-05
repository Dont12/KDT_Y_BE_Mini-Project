package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.order.Order;
import com.fastcampus.reserve.domain.order.OrderReader;
import com.fastcampus.reserve.domain.order.dto.ReserveDateDto;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import java.time.LocalDate;
import java.util.List;
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
    private final RoomReader roomReader;

    @Override
    public Page<Order> findAllWithOrderItem(Long userId, Pageable pageable) {
        return orderRepository.findAllWithOrderItem(userId, pageable);
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

    @Override
    public boolean isPayment(List<ReserveDateDto> reserveDates) {
        Long roomId = Long.MIN_VALUE;
        int stock = 0;
        for (ReserveDateDto reserveDate : reserveDates) {
            if (!roomId.equals(reserveDate.roomId())) {
                stock = getRoomStock(reserveDate);
            }

            int orderCount = orderItemRepository.countReserveStatus(reserveDate);
            if (stock - orderCount < 1) {
                return false;
            }
        }
        return true;
    }

    private Integer getRoomStock(ReserveDateDto reserveDate) {
        return roomReader.findById(reserveDate.roomId()).getStock();
    }
}
