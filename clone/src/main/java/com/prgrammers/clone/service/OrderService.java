package com.prgrammers.clone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.repository.OrderRepository;

@Transactional(readOnly = true)
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductService productService;

	public OrderService(OrderRepository orderRepository, ProductService productService) {
		this.orderRepository = orderRepository;
		this.productService = productService;
	}

	@Transactional
	public Order createOrder(Order order) {
		List<OrderItem> orderItems = order.getOrderItems();

		orderItems.forEach(orderItem -> {
			Product product = productService.getProduct(orderItem.productId());
			product.reduce(orderItem.quantity());
			productService.reduceQuantity(product);
		});

		return orderRepository.insert(order);
	}

	public Order getOrderHistories(Email email) {
		orderRepository.findByEmail(email);
		return null;
	}
}
