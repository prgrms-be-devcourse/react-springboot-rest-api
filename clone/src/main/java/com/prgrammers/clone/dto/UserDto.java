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

public class UserDto {

	@ToString
	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class UserOrderResponse {
		private UUID orderId;
		private String address;
		private String postcode;
		private OrderStatus orderStatus;
		private LocalDateTime createdAt;
		private List<UserDto.UserOrderItemResponse> userOrderItemResponse;

		@Builder
		public UserOrderResponse(
				UUID orderId,
				String address,
				String postCode,
				OrderStatus orderStatus,
				List<UserDto.UserOrderItemResponse> userOrderItemResponse,
				LocalDateTime createdAt
		) {
			this.orderId = orderId;
			this.address = address;
			this.postcode = postCode;
			this.orderStatus = orderStatus;
			this.userOrderItemResponse = userOrderItemResponse;
			this.createdAt = createdAt;
		}

		public void addUserOrderItemResponse(List<UserDto.UserOrderItemResponse> userOrderItemResponse) {
			this.userOrderItemResponse = userOrderItemResponse;
		}
	}

	@ToString
	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class UserOrderItemResponse{
		private UUID productId;
		private Category category;
		private long price;
		private long quantity;

		@Builder
		public UserOrderItemResponse(UUID productId, Category category, long price, long quantity) {
			this.productId = productId;
			this.category = category;
			this.price = price;
			this.quantity = quantity;
		}
	}
}
