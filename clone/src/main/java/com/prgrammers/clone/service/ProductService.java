package com.prgrammers.clone.service;

import java.util.List;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;

public interface ProductService {

	List<Product> getProductByCategory(Category category);

	List<Product> getAllProducts();

	Product create(String productName, Category category, long price);

	Product create(String productName, Category category, long price, String description);

}
