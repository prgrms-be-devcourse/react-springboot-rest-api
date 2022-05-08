package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.prgrammers.clone.exception.DomainException;

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
	public Order(UUID orderId, Email email, String address, String postcode, List<OrderItem> orderItems,
			OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.orderId = orderId;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Order create(Email email, String address, String postcode, List<OrderItem> orderItems) {
		return Order.builder()
				.orderId(UUID.randomUUID())
				.email(email)
				.address(address)
				.postcode(postcode)
				.orderItems(orderItems)
				.orderStatus(OrderStatus.ACCEPT)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	public long calculateTotalPrice() {
		return this.orderItems.stream()
				.map(OrderItem::getTotalPrice)
				.reduce(0L, Long::sum);
	}

	public void addItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderStatus updateOrderState() {
		if (!this.orderStatus.isValidCancel()) {
			throw new DomainException.NotProperCancelPolicy("배송전인 상태에서만 취소가 가능합니다.");
		}

		this.orderStatus = OrderStatus.CANCEL;

		return this.orderStatus;
	}
}
