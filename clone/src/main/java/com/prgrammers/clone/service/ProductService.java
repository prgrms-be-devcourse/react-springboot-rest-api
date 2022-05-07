package com.prgrammers.clone.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.exception.ServiceException;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.repository.ProductRepository;

@Transactional(readOnly = true)
@Service
public class ProductService {
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getProductByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product create(String productName, Category category, long price) {
		return null;
	}

	@Transactional
	public Product create(String productName, Category category, long price, String description) {

		return null;
	}

	@Transactional
	public Product create(Product createProduct) {
		return productRepository.insert(createProduct);
	}

	@Transactional
	public Product update(Product updateProduct) {
		Product product = productRepository.findById(updateProduct.getProductId())
				.orElseThrow(() -> new ServiceException.NotFoundResourceException("존재하지 않는 상품입니다."));

		Product update = product.updateInformation(updateProduct);
		return productRepository.update(update);
	}

	@Transactional
	public void delete(UUID productId) {
		if (productRepository.isExists(productId)) {
			throw new ServiceException.NotFoundResourceException("존재하지 않는 상품입니다.");
		}

		productRepository.delete(productId);
	}

	public Product getProduct(UUID productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ServiceException.NotFoundResourceException("존재하지 않는 상품입니다."));
	}

	public void reduceQuantity(Product product) {
		productRepository.update(product);
	}
}
