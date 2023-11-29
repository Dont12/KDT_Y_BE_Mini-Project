package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(String category, Pageable pageable);

    Page<Product> findAllByArea(String area, Pageable pageable);

    Page<Product> findAllByAreaAndCategory(String area, String category, Pageable pageable);
}
