package com.fastcampus.reserve.interfaces.product.dto.response;

import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record ProductResponse (
        @NotNull Long id,
        @NotNull String name,
        Integer minPrice,
        String imageUrl
){
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getMinPrice(),
                product.getImages().get(0).getUrl()
        );

}



}
