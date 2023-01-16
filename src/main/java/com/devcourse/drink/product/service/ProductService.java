package com.devcourse.drink.product.service;

import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getProductByCategory(Category category);

    List<Product> getAllProduct();

    Product createProduct(String name, Category category, long price, String description);

    Product updateProduct(UUID productId, String name, Category category, long price, String description);

    void deleteProduct(UUID productId);
}
