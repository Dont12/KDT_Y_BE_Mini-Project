package com.fastcampus.reserve.infrestructure.product.room;

import com.fastcampus.reserve.domain.product.room.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.images WHERE r.id = :id")
    Optional<Room> findByIdWithImage(@Param("id") Long id);
}
