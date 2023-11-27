package com.fastcampus.reserve.infrestructure.product;


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
        return null;
    }

    @Override
    public List<Product> getAllProduct(LocalDate checkIn,
                                       LocalDate checkOut,
                                       String category,
                                       String areaCode,
                                       int page,
                                       int pageSize) {
        return productRepository.findAll();
    }


}
