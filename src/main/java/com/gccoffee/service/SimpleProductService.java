package com.gccoffee.service;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import com.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class SimpleProductService implements ProductService {
    private final ProductRepository repository;

    public SimpleProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        return repository.insert(new Product(UUID.randomUUID(), productName, category, price));
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        return repository.insert(new Product(UUID.randomUUID(), productName, category, price, description,
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
    }
}
