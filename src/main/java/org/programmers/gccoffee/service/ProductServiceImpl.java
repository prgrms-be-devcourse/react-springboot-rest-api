package org.programmers.gccoffee.service;

import lombok.RequiredArgsConstructor;
import org.programmers.gccoffee.model.Category;
import org.programmers.gccoffee.model.Product;
import org.programmers.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        return productRepository.insert(new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime.now(), LocalDateTime.now()));
    }
}
