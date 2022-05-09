package com.gccoffee.controller;

import com.gccoffee.model.Category;

public record CreateProductRequest(
    String productName,
    Category category,
    long price,
    String description) {}
