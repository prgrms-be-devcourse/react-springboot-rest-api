package com.prgrammers.clone.dto;

import java.util.List;
import java.util.UUID;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.OrderStatus;

import lombok.Builder;

public class OrderDto {

	@Builder
	public record Create(String email,
						 String address,
						 String postcode,
						 List<CreateOrderItem> orderItems) {
	}

	public record CreateOrderItem(UUID productId,
								  Category category,
								  long price,
								  int quantity
	) {
	}

	@Builder
	public record Response(
			String email,
			String address,
			String postcode,
			OrderStatus orderStatus,
			long totalPrice,
			List<ResponseOrderItem> orderItems
	) {
	}

	@Builder
	public record ResponseOrderItem(UUID productId,
									Category category,
									long price,
									int quantity,
									long summary
	) {
	}
}
