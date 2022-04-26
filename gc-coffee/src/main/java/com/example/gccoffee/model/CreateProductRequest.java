package com.example.gccoffee.model;

public record CreateProductRequest(
        String productName,
        Category category,
        long price,
        String description
){

}
