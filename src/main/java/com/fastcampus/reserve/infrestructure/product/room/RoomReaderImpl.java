package com.fastcampus.reserve.infrestructure.product.room;

import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomReaderImpl implements RoomReader {

    @Override
    public Room findByIdWithImage(Long id) {
        return null;
    }
}
