package com.fastcampus.reserve.domain.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;
import java.util.List;

public record ProductDto(
    Long id,
    String name,
    String zipCode,
    String address,
    String description,
    String longitude,
    String latitude,
    List<String> imageUrls
) {
    public static ProductDto from(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getZipCode(),
            product.getAddress(),
            product.getDescription(),
            product.getLongitude(),
            product.getLatitude(),
            product.getImageUrls()
        );
    }
}
