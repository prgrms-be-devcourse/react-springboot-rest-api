package com.example.gccoffeemanagement.model;

import java.util.UUID;

public record OrderItem(UUID productId, Category category, long price, int quantity) {
}
