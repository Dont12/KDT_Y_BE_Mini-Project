package com.fastcampus.reserve.domain.order;

public interface OrderReader {

    Order findByIdWithOrderItem(Long orderId);
}
