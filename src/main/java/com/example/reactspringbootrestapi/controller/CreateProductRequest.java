package com.example.reactspringbootrestapi.controller;

import com.example.reactspringbootrestapi.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
