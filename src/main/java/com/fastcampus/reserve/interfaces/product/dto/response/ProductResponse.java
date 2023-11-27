package com.fastcampus.reserve.interfaces.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    @NotNull
    private final Long id;

    @NotNull
    private final String name;

    private final Integer minPrice;

    private final String imageUrl;

    @Builder
    public ProductResponse(Long id, String name, Integer minPrice, String imageUrl) {
        this.id = id;
        this.name = name;
        this.minPrice = minPrice;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .minPrice(product.getMinPrice())
                .imageUrl(product.getImages().get(0).getUrl())
                .build();
    }
}