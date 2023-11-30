package com.fastcampus.reserve.domain.product.room;

import com.fastcampus.reserve.domain.product.Product;
import java.util.List;

public interface RoomReader {

    Room findByIdWithImage(Long id);

    List<Room> findAllByProduct(Product product);
}
