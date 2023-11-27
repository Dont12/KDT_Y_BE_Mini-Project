package com.fastcampus.reserve.mock;

import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class FakeRoomReader implements RoomReader {

    @Override
    public Room findByIdWithImage(Long id) {
        RoomImage roomImage = RoomImage.builder()
                .url("http://www.image.co.kr")
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

        return room;
    }
}
