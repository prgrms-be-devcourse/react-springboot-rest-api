package com.prgrammers.clone.controller;

import com.prgrammers.clone.model.Category;

public record CreateProduct(String productName, Category category, long price, String description) {
}
