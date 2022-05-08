package com.prgrammers.clone.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.exception.ServiceException;
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
			productService.reduceUpdate(product);
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

	@Transactional
	public void cancel(UUID orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ServiceException.NotFoundResourceException("order 관한 정보를 찾을 수 없습니다."));

		order.updateOrderState();
		orderRepository.update(order);

		List<OrderItem> orderItems = orderItemService.getOrderItems(orderId);

		List<UUID> productIds = orderItems.stream()
				.map(OrderItem::productId)
				.toList();
		// todo : 1:1인데 .. stream으로 하는 아이디어가 없음
		Map<UUID, Product> products = new HashMap<>();
		productService.getProductsByIds(productIds)
				.forEach(product -> products.put(product.getProductId(), product));
		orderItems.forEach(orderItem -> {
			Product updatingProduct = products.get(orderItem.productId());
			updatingProduct.addQuantity(orderItem);
			productService.update(updatingProduct);
		});

	}
}
