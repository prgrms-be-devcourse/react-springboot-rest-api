package com.devcourse.drink.product.service;

import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;
import com.devcourse.drink.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, Category category, long price, String description) {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(UUID.randomUUID(), name, category, price, description, now, now);

        return productRepository.insert(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
