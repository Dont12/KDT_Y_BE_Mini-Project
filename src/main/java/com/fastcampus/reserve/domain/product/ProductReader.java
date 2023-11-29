package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ProductReader {

    Product getProduct(Long id);

    Page<Product> getAllProduct(ProductListOptionDto dto);

    Product findByIdWithImage(Long id);
}
