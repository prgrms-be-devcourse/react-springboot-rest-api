package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Order {
	private final UUID orderId;
	private final Email email;
	private final String address;
	private final String postcode;
	private List<OrderItem> orderItems;
	private OrderStatus orderStatus;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	private Order(Email email, String address, String postcode, List<OrderItem> orderItems, OrderStatus orderStatus) {
		this.orderId = UUID.randomUUID();
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public static Order createOrder(Email email,String address,String postcode,List<OrderItem> orderItems,OrderStatus orderStatus) {
		return Order.builder()
				.email(email)
				.address(address)
				.postcode(postcode)
				.orderItems(orderItems)
				.orderStatus(orderStatus)
				.build();
	}

	private Order(UUID orderId, Email email, String address, String postcode,
			OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.orderId = orderId;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Order mapToDomain(UUID orderId, Email email, String address, String postcode,
			OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {

		return new Order(orderId, email, address, postcode, orderStatus, createdAt, updatedAt);
	}

	public long calculateTotalPrice() {
		return this.orderItems.stream()
				.map(OrderItem::getTotalPrice)
				.reduce(0L, Long::sum);
	}

}
