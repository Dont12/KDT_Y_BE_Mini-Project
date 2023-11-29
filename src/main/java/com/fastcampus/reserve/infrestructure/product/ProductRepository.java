package com.fastcampus.reserve.infrestructure.product;

import com.fastcampus.reserve.domain.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

    List<Product> findAllByCategory(String category);

    @Query("SELECT p "
            + "FROM Product p "
            + "JOIN FETCH p.images "
            + "WHERE p.id = :id")
    Optional<Product> findByIdWithImage(@Param("id") Long Id);
}
