package com.example.gccoffe.controller;

import com.example.gccoffe.model.Category;

public class CreateProductRequest {
    private final String productName;
    private final Category category;
    private final long price;
    private final String description;

    public CreateProductRequest(String productName, Category category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getProductName() {
        return productName;
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
}
