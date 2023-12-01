package com.fastcampus.reserve.infrestructure.order;

import com.fastcampus.reserve.domain.order.dto.ReserveDateDto;
import com.fastcampus.reserve.domain.order.orderitem.OrderItem;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    @Query(
            "SELECT COUNT(o) FROM OrderItem o "
                    + "WHERE o.checkInDate <= :checkInDate "
                    + "AND o.checkOutDate >= :checkOutDate "
                    + "AND o.roomId = :roomId"
    )
    int countByTimeAndRoomId(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    @Query("SELECT COUNT(oi) "
            + "FROM OrderItem oi "
            + "WHERE oi.roomId = :#{#reserveDate.roomId} "
            + "AND :#{#reserveDate.date} BETWEEN oi.checkInDate AND oi.checkOutDate"
    )
    int countReserveStatus(
            @Param("reserveDate")ReserveDateDto reserveDate
    );
}
