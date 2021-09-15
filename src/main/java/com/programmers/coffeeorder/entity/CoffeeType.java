package com.programmers.coffeeorder.entity;

public enum CoffeeType {
    LATTE, CAPPUCCINO, MACCHIATO, BLACK;

    public static CoffeeType of(String input) {
        return CoffeeType.valueOf(input.toUpperCase());
    }
}
