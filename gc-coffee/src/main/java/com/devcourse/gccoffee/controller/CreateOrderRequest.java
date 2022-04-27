package com.devcourse.gccoffee.controller;

import com.devcourse.gccoffee.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(
    String email, String postcode, List<OrderItem> orderItems
) {
}
