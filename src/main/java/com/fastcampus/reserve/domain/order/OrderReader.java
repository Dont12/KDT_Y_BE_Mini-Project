package com.fastcampus.reserve.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReader {

    Page<Order> findAll(Pageable pageable);

    Order findByIdWithOrderItem(Long orderId);
}
