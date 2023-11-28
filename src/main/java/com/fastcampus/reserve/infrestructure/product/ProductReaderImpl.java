package com.fastcampus.reserve.infrestructure.product;


import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductReader;
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
        if (haveAreaAndCategory(dto)) {
            return productRepository.findAllByAreaAndCategory(
                    dto.area(), dto.category(), dto.page(), dto.pageSize());
        } else if (hasArea(dto)) {
            return productRepository.findAllByArea(dto.area(), dto.page(), dto.pageSize());
        } else if (hasCategory(dto)) {
            return productRepository.findAllByCategory(dto.category(), dto.page(), dto.pageSize());
        }
        return productRepository.findAll();
    }

    @Override
    public Product findByIdWithImage(Long id) {
        return null;
    }

    private boolean hasCategory(ProductListOptionDto dto) {
        return dto.category() != null && !dto.category().isEmpty();
    }

    private boolean hasArea(ProductListOptionDto dto) {
        return dto.area() != null && !dto.area().isEmpty();
    }

    private boolean haveAreaAndCategory(ProductListOptionDto dto) {
        return dto.area() != null && !dto.area().isEmpty()
                && dto.category() != null && !dto.category().isEmpty();
    }
}
