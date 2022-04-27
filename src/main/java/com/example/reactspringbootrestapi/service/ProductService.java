package com.example.reactspringbootrestapi.service;

import com.example.reactspringbootrestapi.model.Category;
import com.example.reactspringbootrestapi.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductByCategory(Category category);

    List<Product> getAllProduct();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);
}
