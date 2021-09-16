package com.programmers.coffeeorder.entity;

public enum OrderStatus {
    NOT_DELIVERED,
    DELIVERING,
    DELIVERED;

    public static OrderStatus of(String input) {
        try {
            return OrderStatus.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return OrderStatus.NOT_DELIVERED;
        }
    }
}
