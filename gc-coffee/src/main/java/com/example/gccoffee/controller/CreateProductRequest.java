package com.example.gccoffee.controller;

import com.example.gccoffee.model.Category;

public class CreateProductRequest {
    public String productName;
    public Category category;
    public long price;
    public String description;

    public CreateProductRequest(String productName, Category category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
