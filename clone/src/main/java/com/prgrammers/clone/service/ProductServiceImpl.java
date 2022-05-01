package com.prgrammers.clone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.repository.ProductRepository;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getProductByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Transactional
	@Override
	public Product create(String productName, Category category, long price) {
		Product newProduct = new Product(UUID.randomUUID(), productName, category, price);

		return productRepository.insert(newProduct);
	}

	@Transactional
	@Override
	public Product create(String productName, Category category, long price, String description) {
		Product newProduct = new Product(UUID.randomUUID(), productName, category, price, description,
				LocalDateTime.now(), LocalDateTime.now());

		log.info("domain product -> {}",newProduct);

		return productRepository.insert(newProduct);
	}
}
