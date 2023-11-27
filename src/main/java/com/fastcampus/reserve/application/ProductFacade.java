package com.fastcampus.reserve.application;


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
    private final ProductDtoMapper productDtoMapper;

    public List<ProductResponse> getProducts(
            LocalDate checkIn,
            LocalDate checkOut,
            String category,
            String areaCode,
            int page,
            int pageSize) {
        List<Product> allProducts = productService.getAllProducts(
                checkIn, checkOut, category, areaCode, page, pageSize);
        return allProducts.stream()
                .map(productDtoMapper::of)
                .collect(Collectors.toList());
    }

}
