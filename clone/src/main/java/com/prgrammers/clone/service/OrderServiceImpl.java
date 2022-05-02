package com.prgrammers.clone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.OrderStatus;
import com.prgrammers.clone.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
		Order order = new Order(
				UUID.randomUUID(),
				email,
				address,
				postcode,
				orderItems,
				OrderStatus.ACCEPT,
				LocalDateTime.now(),
				LocalDateTime.now()
		);

		return orderRepository.insert(order);
	}
}
