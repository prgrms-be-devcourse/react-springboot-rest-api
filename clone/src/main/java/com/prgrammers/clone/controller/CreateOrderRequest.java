package com.prgrammers.clone.controller;

import java.util.List;

import com.prgrammers.clone.model.OrderItem;

public record CreateOrderRequest(
		String email, String address, String postcode, List<OrderItem> orderItems
) {
}
