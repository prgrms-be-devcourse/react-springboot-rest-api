package com.example.gccoffeemanagement.controller;

import com.example.gccoffeemanagement.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {

}
