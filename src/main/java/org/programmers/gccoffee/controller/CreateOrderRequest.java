package org.programmers.gccoffee.controller;

import org.programmers.gccoffee.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
}
