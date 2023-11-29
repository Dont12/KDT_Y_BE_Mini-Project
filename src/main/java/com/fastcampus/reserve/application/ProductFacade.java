package com.fastcampus.reserve.application;


import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFacade {
    private final ProductService productService;

    public List<ProductDto> getProducts(ProductListOptionDto dto) {
        return productService.getAllProducts(dto);
    }

}
