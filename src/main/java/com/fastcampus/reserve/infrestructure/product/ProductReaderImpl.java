package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReaderImpl implements ProductReader {

    @Override
    public Product findByIdWithImage(Long id) {
        return null;
    }
}
