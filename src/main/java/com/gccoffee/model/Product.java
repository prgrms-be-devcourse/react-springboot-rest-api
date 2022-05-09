package com.gccoffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setPrice(long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
