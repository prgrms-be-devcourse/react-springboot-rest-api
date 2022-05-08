package com.example.gccoffeemanagement.controller;

import com.example.gccoffeemanagement.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(
        String email, String address, String postcode, List<OrderItem> orderItems
) {
}
