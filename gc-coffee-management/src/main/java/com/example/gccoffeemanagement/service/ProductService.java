package com.example.gccoffeemanagement.service;

import com.example.gccoffeemanagement.model.Category;
import com.example.gccoffeemanagement.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);
}
