package com.fastcampus.reserve.domain.product;

import java.time.LocalDate;
import java.util.List;

public interface ProductReader {

    Product getProduct(Long id);

    List<Product> getAllProduct(LocalDate checkIn,
                                LocalDate checkOut,
                                String category,
                                String areaCode,
                                int page,
                                int pageSize);

    Product findByIdWithImage(Long id);
}
