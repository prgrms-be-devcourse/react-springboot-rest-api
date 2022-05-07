package com.prgrammers.clone.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrammers.clone.converter.OrderConverter;
import com.prgrammers.clone.dto.OrderDto;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderRestController {

	private static final Logger log = LoggerFactory.getLogger(OrderRestController.class);
	private final OrderService orderService;
	private final OrderConverter orderConverter;

	@PostMapping("")
	public ResponseEntity<OrderDto.Response> createOrder(@RequestBody OrderDto.Create orderRequest) {
		Order order = orderConverter.createDtoToDomain()
				.convert(orderRequest);
		Order createdOrder = orderService.createOrder(order);
		OrderDto.Response response = orderConverter.domainToResponse().convert(createdOrder);
		return ResponseEntity.ok(response);
	}

}
