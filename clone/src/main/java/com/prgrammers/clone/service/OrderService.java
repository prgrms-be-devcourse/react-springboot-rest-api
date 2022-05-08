package com.prgrammers.clone.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductService productService;
	private final OrderItemService orderItemService;

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

	public List<Order> getOrderHistories(Email email) {
		List<Order> orders = orderRepository.findByEmail(email);

		if (orders.isEmpty()) {
			return List.of();
		}

		List<UUID> orderIds = orders.stream()
				.map(Order::getOrderId)
				.toList();
		List<OrderItem> orderItems = orderItemService.getOrderItems(orderIds);
		Map<UUID, List<OrderItem>> orderItemsGroups = orderItems.stream()
				.collect(Collectors.groupingBy(OrderItem::orderId));
		orders.forEach(order -> {
			order.addItems(orderItemsGroups.get(order.getOrderId()));
		});

		return orders;
	}
}
