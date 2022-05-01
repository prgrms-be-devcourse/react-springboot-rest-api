package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
	private final UUID orderId;
	private final Email email;
	private final String address;
	private final String postCOde;
	private final List<OrderItem> orderItems;
	private OrderStatus orderStatus;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Order(UUID orderId, Email email, String address, String postCOde, List<OrderItem> orderItems,
			OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.orderId = orderId;
		this.email = email;
		this.address = address;
		this.postCOde = postCOde;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public Email getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPostCOde() {
		return postCOde;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
