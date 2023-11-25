package com.fastcampus.reserve.interfaces.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;
import jakarta.validation.constraints.NotNull;

public record ProductResponse(
        @NotNull Long id,
        @NotNull String name,
        Integer minPrice,
        String imageUrl
) {
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getMinPrice(),
                product.getImages().get(0).getUrl()
        );

    }
}
