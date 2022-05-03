package com.dojinyou.devcourse.gccoffee.repository;

import com.dojinyou.devcourse.gccoffee.model.Category;
import com.dojinyou.devcourse.gccoffee.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository {
    List<Product> findAll();

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    void deleteAll();

}
