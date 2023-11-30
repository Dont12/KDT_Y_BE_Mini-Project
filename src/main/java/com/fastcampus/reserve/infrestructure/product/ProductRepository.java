package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByCategory(String category, Pageable pageable);

    Page<Product> findAllByArea(String area, Pageable pageable);

    Page<Product> findAllByAreaAndCategory(String area, String category, Pageable pageable);

    @Query("SELECT p "
            + "FROM Product p "
            + "JOIN FETCH p.images "
            + "WHERE p.id = :id")
    Optional<Product> findByIdWithImage(@Param("id") Long id);
}
