package com.fastcampus.reserve.application;


import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductService;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
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

    public List<ProductResponse> getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return allProducts.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

}
