package com.fastcampus.reserve.domain.product;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductReader productReader;

    public List<Product> getAllProducts(LocalDate checkIn,
                                        LocalDate checkOut,
                                        String category,
                                        String areaCode,
                                        int page,
                                        int pageSize) {
        return productReader.getAllProduct(checkIn, checkOut, category, areaCode, page, pageSize);
    }
}
