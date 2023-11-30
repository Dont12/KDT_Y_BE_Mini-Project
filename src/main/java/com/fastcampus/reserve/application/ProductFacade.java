package com.fastcampus.reserve.application;

import com.fastcampus.reserve.domain.order.OrderService;
import com.fastcampus.reserve.domain.product.ProductService;
import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDetailDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductSummaryDto;
import com.fastcampus.reserve.domain.product.dto.response.RoomDetailDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {

    private final ProductService productService;
    private final OrderService orderService;

    public List<ProductSummaryDto> getProducts(ProductListOptionDto dto) {
        return productService.getAllProducts(dto);
    }

    public ProductDetailDto getProductDetail(
        Long productId, LocalDate checkInDate, LocalDate checkOutDate
    ) {
        ProductDto product = productService.getProductDto(productId);
        List<RoomDetailDto> roomDetails = productService.getRoomDetails(productId, checkInDate);

        List<RoomDetailDto> stockUpdatedRoomDetails = new ArrayList<>();
        for (RoomDetailDto roomDetail : roomDetails) {
            Long roomId = roomDetail.id();
            int stock = roomDetail.stock();
            int orderedStock =
                orderService.calcOrderedStock(roomId, checkInDate, checkOutDate);

            RoomDetailDto updatedRoomDetailDto = roomDetail.updateStock(stock - orderedStock);
            stockUpdatedRoomDetails.add(updatedRoomDetailDto);
        }

        return ProductDetailDto.from(product, stockUpdatedRoomDetails);
    }
}
