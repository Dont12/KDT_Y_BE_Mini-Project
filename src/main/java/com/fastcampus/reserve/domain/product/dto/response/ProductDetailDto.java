package com.fastcampus.reserve.domain.product.dto.response;

import java.util.List;

public record ProductDetailDto(
    Long id,
    String name,
    String zipCode,
    String address,
    String description,
    String longitude,
    String latitude,
    List<String> imageUrls,
    List<RoomDetailDto> rooms
) {

    public static ProductDetailDto from(
        ProductDto product,
        List<RoomDetailDto> roomDetails
    ) {
        return new ProductDetailDto(
            product.id(),
            product.name(),
            product.zipCode(),
            product.address(),
            product.description(),
            product.longitude(),
            product.latitude(),
            product.imageUrls(),
            roomDetails
        );
    }
}
