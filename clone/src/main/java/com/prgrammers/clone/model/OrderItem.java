package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderItem(UUID productId,
						Category category,
						long price,
						long quantity,
						LocalDateTime createdAt,
						LocalDateTime updatedAt) {
	public long getTotalPrice() {
		return price * quantity;
	}
}
