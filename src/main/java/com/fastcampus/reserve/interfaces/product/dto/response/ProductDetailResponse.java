package com.fastcampus.reserve.interfaces.product.dto.response;

import java.util.List;

public record ProductDetailResponse(
    Long id,
    String name,
    String zipCode,
    String address,
    String description,
    String longitude,
    String latitude,
    List<String> imageUrls,
    List<RoomDetailResponse> rooms
) {
}
