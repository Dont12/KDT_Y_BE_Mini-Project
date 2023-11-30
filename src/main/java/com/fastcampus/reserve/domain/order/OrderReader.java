package com.fastcampus.reserve.domain.order;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReader {

    Page<Order> findAllWithOrderItem(Pageable pageable);

    Order findByIdWithOrderItem(Long orderId);

    int calcOrderedStock(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
