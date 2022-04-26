package com.devcourse.gccoffee.controller;

import com.devcourse.gccoffee.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
