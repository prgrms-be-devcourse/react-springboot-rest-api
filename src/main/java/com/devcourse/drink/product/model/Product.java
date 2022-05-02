package com.devcourse.drink.product.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.drink.config.error.ErrorType.PRICE_NEGATIVE_VALUE;

public class Product {

    private final UUID productId;
    private String name;
    private Category category;
    private long price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(UUID productId, String name, Category category, long price, String description, LocalDateTime createdAt) {
        productPriceCheck(price);
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    private void productPriceCheck(long price) {
        if (price < 0) {
            throw new IllegalArgumentException(PRICE_NEGATIVE_VALUE.message());
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPrice(long price) {
        productPriceCheck(price);
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
