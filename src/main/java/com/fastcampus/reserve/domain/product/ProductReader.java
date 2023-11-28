package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import java.time.LocalDate;
import java.util.List;

public interface ProductReader {

    Product getProduct(Long id);

    List<Product> getAllProduct(ProductListOptionDto dto);

    Product findByIdWithImage(Long id);
}
