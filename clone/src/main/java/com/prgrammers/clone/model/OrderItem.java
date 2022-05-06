package com.prgrammers.clone.model;

import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderItem(UUID productId, Category category, long price, int quantity) {
	public long getTotalPrice() {
		return price * quantity;
	}
}
