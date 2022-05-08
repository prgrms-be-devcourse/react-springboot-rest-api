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

	boolean isExists(UUID productId);

	Optional<Product> findById(UUID productId);

	List<Product> findByName(String productName);

	List<Product> findByCategory(Category category);

	void deleteAll();

	void deleteById(UUID productId);

	List<Product> findById(List<UUID> productIds);
}
