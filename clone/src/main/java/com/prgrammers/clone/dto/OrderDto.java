package com.prgrammers.clone.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.OrderStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class OrderDto {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@ToString
	@Getter
	public static class Create {
		private String email;
		private String address;
		private String postcode;
		private List<CreateOrderItem> orderItems;

		private OrderStatus orderStatus = OrderStatus.ACCEPT;

		private LocalDateTime createdAt = LocalDateTime.now();
		private LocalDateTime updatedAt = LocalDateTime.now();

		@Builder

		public Create(String email,
				String address,
				String postcode,
				List<CreateOrderItem> orderItems) {
			this.email = email;
			this.address = address;
			this.postcode = postcode;
			this.orderItems = orderItems;
		}
	}

	@ToString
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public static class CreateOrderItem {
		private UUID productId;
		private Category category;
		private long price;
		private int quantity;

		@Builder
		public CreateOrderItem(UUID productId,
				Category category,
				long price,
				int quantity
		) {
			this.productId = productId;
			this.category = category;
			this.price = price;
			this.quantity = quantity;
		}
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
									long quantity,
									long summary
	) {
	}
}
