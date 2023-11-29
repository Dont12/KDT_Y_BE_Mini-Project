package com.fastcampus.reserve.infrestructure.product;


import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ProductReaderImpl implements ProductReader {

    private final ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Page<Product> getAllProduct(ProductListOptionDto dto) {
        Pageable pageable = PageRequest.of(dto.page(), dto.pageSize());
        return filterProduct(dto, pageable);
    }

    @Override
    public Product findByIdWithImage(Long id) {
        return null;
    }

    private Page<Product> filterProduct(ProductListOptionDto dto, Pageable pageable) {
        if (haveAreaAndCategory(dto)) {
            return productRepository.findAllByAreaAndCategory(
                    dto.area(), dto.category(), pageable);
        } else if (hasArea(dto)) {
            return productRepository.findAllByArea(dto.area(), pageable);
        } else if (hasCategory(dto)) {
            return productRepository.findAllByCategory(dto.category(), pageable);
        }
        return productRepository.findAll(pageable);
    }

    private boolean hasCategory(ProductListOptionDto dto) {
        return !Objects.isNull(dto.category());
    }

    private boolean hasArea(ProductListOptionDto dto) {
        return !Objects.isNull(dto.area());
    }

    private boolean haveAreaAndCategory(ProductListOptionDto dto) {
        return !Objects.isNull(dto.area()) && !Objects.isNull(dto.category());
    }
}
