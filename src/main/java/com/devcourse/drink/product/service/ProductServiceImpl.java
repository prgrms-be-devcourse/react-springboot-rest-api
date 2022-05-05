package com.devcourse.drink.product.service;

import com.devcourse.drink.config.error.ErrorType;
import com.devcourse.drink.config.exception.OrderNotMatchedException;
import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;
import com.devcourse.drink.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.drink.config.error.ErrorType.ORDER_NOT_MATCHED;

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
    public Product updateProduct(UUID productId, String name, Category category, long price, String description) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            Product modifyProduct = product.get();
            modifyProduct.setName(name);
            modifyProduct.setCategory(category);
            modifyProduct.setPrice(price);
            modifyProduct.setDescription(description);
            return productRepository.update(modifyProduct);
        } else {
            throw new OrderNotMatchedException(ORDER_NOT_MATCHED);
        }
    }


    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
