package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "coffeeName", "coffeeType"})
public class CoffeeProduct {
    private Long id;
    private String coffeeName;
    private CoffeeType coffeeType;
    private int price;
    private String description;

    public DTO toDTO() {
        return new DTO(id, coffeeName, coffeeType.toString(), price);
    }

    public CoffeeProduct(Long id) {
        this.id = id;
    }

    public void update(CoffeeProduct product) {
        this.coffeeName = product.coffeeName;
        this.coffeeType = product.coffeeType;
        this.price = product.price;
        this.description = product.description;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class DTO {
        Long productId;
        String productName;
        String category;
        int price;
    }
}
