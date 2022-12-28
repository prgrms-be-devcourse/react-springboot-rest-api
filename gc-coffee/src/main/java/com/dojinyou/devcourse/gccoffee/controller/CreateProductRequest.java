package com.dojinyou.devcourse.gccoffee.controller;

import com.dojinyou.devcourse.gccoffee.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
