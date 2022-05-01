package com.prgrammers.clone.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;

public interface ProductRepository {

	List<Product> findAll();

	Product insert(Product product);

	Product update(Product product);

	Optional<Product> findById(UUID productId);

	Optional<Product> findByName(String productName);

	Optional<Product> findByCategory(Category category);

	void deleteAll();

}
