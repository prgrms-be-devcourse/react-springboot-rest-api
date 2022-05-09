package com.devcourse.drink.product.repository;

import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category);

    void deleteById(UUID productId);

    void deleteAll();
}
