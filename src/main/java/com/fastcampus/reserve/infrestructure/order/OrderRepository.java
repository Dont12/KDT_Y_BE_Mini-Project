package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.domain.order.Order;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(
            value = "SELECT o "
                    + "FROM Order o "
                    + "JOIN FETCH o.orderItems oi "
                    + "WHERE o.userId = :userId "
                    + "AND o.createdDate >= :recentMonthAgo",
            countQuery = "SELECT COUNT(o) "
                    + "FROM Order o "
                    + "WHERE o.userId = :userId "
                    + "AND o.createdDate >= :recentMonthAgo"
    )
    Page<Order> findAllWithOrderItem(
            @Param("userId") Long userId,
            @Param("recentMonthAgo") LocalDateTime recentMonthsAgo,
            Pageable pageable
    );

    @Query("SELECT o "
            + "FROM Order o "
            + "JOIN FETCH o.orderItems oi "
            + "WHERE o.id = :id")
    Optional<Order> findByIdWithOrderItem(@Param("id") Long id);
}
