package com.prgrammers.clone.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prgrammers.clone.controller.CreateOrderRequest;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.service.OrderService;

@RestController
public class OrderRestController {
	private final OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/api/v1/orders")
	public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
		return orderService.createOrder(
				new Email(orderRequest.email()),
				orderRequest.address(),
				orderRequest.postcode(),
				orderRequest.orderItems()
		);
	}
}
