package com.fastcampus.reserve.domain.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;

public record ProductSummaryDto(
    Long id,
    String name,
    Integer minPrice,
    String imageUrl
) {
    public static ProductSummaryDto from(Product product) {
        return new ProductSummaryDto(
            product.getId(),
            product.getName(),
            product.getMinPrice(),
            product.getImageUrl()
        );
    }
}
