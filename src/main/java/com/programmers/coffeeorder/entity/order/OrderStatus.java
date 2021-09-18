package com.programmers.coffeeorder.entity.order;

public enum OrderStatus {
    CREATED,
    CANCELLED,
    ACCEPTED;

    public static OrderStatus of(String input) {
        try {
            return OrderStatus.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return OrderStatus.CREATED;
        }
    }
}
