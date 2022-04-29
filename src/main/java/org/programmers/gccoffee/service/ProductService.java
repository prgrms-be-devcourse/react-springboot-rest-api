package org.programmers.gccoffee.service;

import org.programmers.gccoffee.model.Category;
import org.programmers.gccoffee.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price, String description);
}
