package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.domain.order.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT o "
            + "FROM Order o "
            + "JOIN FETCH o.orderItems oi "
            + "WHERE o.id = :id")
    Optional<Order> findByIdWithOrderItem(@Param("id") Long id);
}
