package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoffeeProduct {
    private Long id;
    private String coffeeName;
    private CoffeeType coffeeType;
    private int price;
    private String description;

    public DTO toDTO() {
        return new DTO(id, coffeeName, coffeeName.toString(), price);
    }

    @AllArgsConstructor
    public static class DTO {
        long productId;
        String productName;
        String category;
        int price;
    }
}
