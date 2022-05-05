package com.devcourse.drink.product.model;

public record CreateProductRequest(String name, Category category, long price, String description) {
}
