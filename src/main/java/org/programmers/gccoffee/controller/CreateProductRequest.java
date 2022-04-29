package org.programmers.gccoffee.controller;

import org.programmers.gccoffee.model.Category;

public record CreateProductRequest(String productName,
                                   Category category,
                                   long price,
                                   String description) {
}