package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.FacilityInfoDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductSummaryDto;
import com.fastcampus.reserve.domain.product.dto.response.RoomDetailDto;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomReader;
import com.fastcampus.reserve.domain.user.dto.response.RoomDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private final ObjectMapper objectMapper;

    public List<ProductSummaryDto> getAllProducts(ProductListOptionDto dto) {
        return productReader.getAllProduct(dto).stream()
            .map(ProductSummaryDto::from)
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

    public RoomDto getRoomDetail(Long id) {
        Room room = roomReader.findByIdWithImage(id);
        return RoomDto.from(room);
    }

    public ProductDto getProductDto(Long id) {
        Product product = productReader.getProduct(id);
        return ProductDto.from(product);
    }

    public List<RoomDetailDto> getRoomDetails(Long productId, LocalDate reserveDate) {
        Product product = productReader.getProduct(productId);
        List<Room> rooms = roomReader.findAllByProduct(product);

        List<RoomDetailDto> roomDetails = new ArrayList<>();
        for (Room room : rooms) {
            FacilityInfoDto facilityInfo = convertFacilityInfo(room.getRoomFacilities());
            roomDetails.add(RoomDetailDto.from(room, reserveDate, facilityInfo));
        }
        return roomDetails;
    }

    private FacilityInfoDto convertFacilityInfo(String roomFacilities) {
        try {
            return objectMapper.readValue(roomFacilities, FacilityInfoDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
