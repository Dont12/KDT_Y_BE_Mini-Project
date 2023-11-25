package com.fastcampus.reserve.domain.product;

import java.util.List;

public interface ProductReader {

    Product getProduct(Long id);

    List<Product> getAllProduct();
}
