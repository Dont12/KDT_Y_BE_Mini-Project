package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(String category, int page, int pageSize);

    List<Product> findAllByArea(String area, int page, int pageSize);

    List<Product> findAllByAreaAndCategory(String area, String category, int page, int pageSize);
}
