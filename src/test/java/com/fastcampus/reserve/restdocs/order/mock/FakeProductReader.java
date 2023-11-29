package com.fastcampus.reserve.restdocs.order.mock;

import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.ProductReader;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

@Primary
@Component
public class FakeProductReader implements ProductReader {

    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(ProductListOptionDto dto) {
        return null;
    }

    @Override
    public Product findByIdWithImage(Long id) {
        ProductImage productImage = ProductImage.builder()
                .url("https://www.image.co.kr")
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

        ReflectionTestUtils.setField(product, "id", -1L);

        return product;
    }
}
