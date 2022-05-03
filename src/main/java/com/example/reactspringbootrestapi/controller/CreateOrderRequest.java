package com.example.reactspringbootrestapi.controller;

import com.example.reactspringbootrestapi.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(
        String email, String address, String postcode, List<OrderItem> orderItems
) {
}
