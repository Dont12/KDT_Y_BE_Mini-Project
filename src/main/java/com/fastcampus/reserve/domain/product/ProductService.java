package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductReader productReader;

    public List<ProductDto> getAllProducts(ProductListOptionDto dto) {
        return productReader.getAllProduct(dto).stream()
            .map(ProductDto::from)
            .toList();
    }
}
