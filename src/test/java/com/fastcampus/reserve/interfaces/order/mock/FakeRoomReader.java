package com.fastcampus.reserve.interfaces.order.mock;

import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

@Primary
@Component
public class FakeRoomReader implements RoomReader {

    @Override
    public Room findByIdWithImage(Long id) {
        RoomImage roomImage = RoomImage.builder()
                .url("https://www.image.co.kr")
                .build();

        Room room = Room.builder()
                .name("name")
                .price(99000)
                .stock(12)
                .checkInTime("15:00")
                .checkOutTime("12:00")
                .baseGuestCount(2)
                .maxGuestCount(4)
                .build();

        room.addImage(roomImage);

        ReflectionTestUtils.setField(room, "id", -1L);

        return room;
    }
}
