package com.fastcampus.reserve.interfaces.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.room.Room;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailResponse {
    private final Long id;

    private final String name;
    private final String zipCode;
    private final String address;
    private final String description;
    private final String longitude;
    private final String latitude;
    private final List<RoomResponse> rooms;

    @Builder
    public ProductDetailResponse(Long id,
                                 String name,
                                 String zipCode,
                                 String address,
                                 String description,
                                 String longitude,
                                 String latitude,
                                 List<RoomResponse> rooms) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rooms = rooms;
    }

    public static ProductDetailResponse from(Product product) {
        List<Room> rooms1 = product.getRooms();
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .zipCode(product.getZipCode())
                .address(product.getAddress())
                .description(product.getDescription())
                .longitude(product.getLongitude()).latitude(product.getLatitude())
                .build(); // room 들어가야함
    }
}
