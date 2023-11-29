package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import org.springframework.data.domain.Page;

public interface ProductReader {

    Product getProduct(Long id);

    Page<Product> getAllProduct(ProductListOptionDto dto);

    Product findByIdWithImage(Long id);
}
