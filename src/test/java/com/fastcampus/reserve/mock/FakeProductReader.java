package com.fastcampus.reserve.mock;

import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.ProductReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * todo
 * 실제 구현체가 작성되면 삭제 예정
 */
@Primary
@Component
public class FakeProductReader implements ProductReader {

    @Override
    public Product findByIdWithImage(Long id) {
        ProductImage productImage = ProductImage.builder()
                .url("http://www.image.co.kr")
                .build();

        Product product = Product.builder()
                .name("name")
                .category("hotel")
                .description("description")
                .zipCode("zipcode")
                .address("address")
                .longitude("longitude")
                .latitude("latitude")
                .area("area")
                .sigungu("sigungu")
                .build();

        product.addImage(productImage);

        return product;
    }
}
