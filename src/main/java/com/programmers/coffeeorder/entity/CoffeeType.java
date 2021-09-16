package com.programmers.coffeeorder.entity;

public enum CoffeeType {
    LATTE, CAPPUCCINO, MACCHIATO, BLACK;

    public static CoffeeType of(String input) {
        try {
            return CoffeeType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return LATTE;
        }
    }
}
