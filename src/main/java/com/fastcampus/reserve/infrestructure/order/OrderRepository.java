package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.domain.order.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
