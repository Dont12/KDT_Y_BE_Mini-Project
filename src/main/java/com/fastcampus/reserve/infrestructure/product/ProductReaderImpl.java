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
    public Page<Product> getAllProduct(ProductListOptionDto dto) {
        Pageable pageable = PageRequest.of(dto.page(), dto.pageSize());
        return filterProduct(dto, pageable);
    }

    @Override
    public Product findByIdWithImage(Long id) {
        return productRepository.findByIdWithImage(id)
                .orElseThrow(() -> new CustomException(NOT_EXIST_PRODUCT));
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
