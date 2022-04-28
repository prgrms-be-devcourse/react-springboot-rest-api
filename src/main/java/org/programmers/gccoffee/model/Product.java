package org.programmers.gccoffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Product {
    private final UUID productId;
    private String productName;
    private Category category;
    private long price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(UUID productId, String productName, Category category, long price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setProductName(String productName) {
        this.productName = productName;
        setUpdatedAt();
    }

    public void setCategory(Category category) {
        this.category = category;
        setUpdatedAt();
    }

    public void setPrice(long price) {
        this.price = price;
        setUpdatedAt();
    }

    public void setDescription(String description) {
        this.description = description;
        setUpdatedAt();
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}