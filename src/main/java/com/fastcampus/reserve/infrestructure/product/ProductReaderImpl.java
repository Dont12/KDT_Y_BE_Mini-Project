package com.fastcampus.reserve.infrestructure.product;

import static com.fastcampus.reserve.common.response.ErrorCode.NOT_EXIST_PRODUCT;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReaderImpl implements ProductReader {

    private final ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_EXIST_PRODUCT));
    }

    @Override
    public List<Product> getAllProduct(ProductListOptionDto dto) {
        return productRepository.findAll();
    }

    @Override
    public Product findByIdWithImage(Long id) {
        return productRepository.findByIdWithImage(id)
                .orElseThrow(() -> new CustomException(NOT_EXIST_PRODUCT));
    }
}
