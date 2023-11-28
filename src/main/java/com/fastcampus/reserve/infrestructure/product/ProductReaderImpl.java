package com.fastcampus.reserve.infrestructure.product;


import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public List<Product> getAllProduct(ProductListOptionDto dto) {
        return productRepository.findAll();
    }

    @Override
    public Product findByIdWithImage(Long id) {
        return null;
    }
}
