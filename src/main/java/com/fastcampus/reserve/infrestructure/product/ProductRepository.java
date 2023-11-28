package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(String category, Pageable pageable);

    List<Product> findAllByArea(String area, Pageable pageable);

    List<Product> findAllByAreaAndCategory(String area, String category, Pageable pageable);
}
