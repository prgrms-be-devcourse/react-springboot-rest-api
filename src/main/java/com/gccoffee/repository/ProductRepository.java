package com.gccoffee.repository;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();
    
    Product insert(Product product);
    
    Product update(Product product);
    
    Optional<Product> findById(UUID productId);
    
    Optional<Product> findByName(String productName);
    
    List<Product> findByCategory(Category category);
    
    void deleteAll();
}
