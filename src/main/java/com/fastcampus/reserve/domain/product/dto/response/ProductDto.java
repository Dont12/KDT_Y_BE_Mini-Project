package com.fastcampus.reserve.domain.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;

public record ProductDto(
    Long id,
    String name,
    Integer minPrice,
    String imageUrl
) {
    public static ProductDto from(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getMinPrice(),
            product.getImageUrl()
        );
    }
}
