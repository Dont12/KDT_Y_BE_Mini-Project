package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import com.fastcampus.reserve.domain.user.dto.response.CartDetailDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductReader productReader;
    private final RoomReader roomReader;

    public List<ProductDto> getAllProducts(ProductListOptionDto dto) {
        return productReader.getAllProduct(dto).stream()
            .map(ProductDto::from)
            .toList();
    }

    public int calcTotalPrice(List<Long> roomIds) {
        int totalPrice = 0;
        for (Long roomId : roomIds) {
            Room room = roomReader.findByIdWithImage(roomId);
            totalPrice += room.getPrice();
        }
        return totalPrice;
    }

    public CartDetailDto getRoomDetail(Long id) {
        Room room = roomReader.findByIdWithImage(id);
        return CartDetailDto.from(room);
    }
}
