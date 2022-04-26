package com.sdardew.gccoffee.controller;

import com.sdardew.gccoffee.model.Category;

public record CreateProductRequest(String productName, Category category, Long price, String description) {
}