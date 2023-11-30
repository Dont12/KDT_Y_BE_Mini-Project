package com.fastcampus.reserve.infrestructure.product.room;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomReaderImpl implements RoomReader {

    private final RoomRepository roomRepository;

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));
    }

    @Override
    public Room findByIdWithImage(Long id) {
        return roomRepository.findByIdWithImage(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_IMAGE));
    }

    @Override
    public List<Room> findAllByProduct(Product product) {
        return roomRepository.findAllByProduct(product);
    }
}
