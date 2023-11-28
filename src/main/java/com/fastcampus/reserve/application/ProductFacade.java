package com.fastcampus.reserve.application;


import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductService;
import com.fastcampus.reserve.interfaces.product.ProductDtoMapper;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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
