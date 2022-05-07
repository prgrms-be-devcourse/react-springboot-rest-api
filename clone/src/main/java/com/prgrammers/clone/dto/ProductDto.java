package com.prgrammers.clone.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrammers.clone.model.Category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Create {
		private UUID productId = UUID.randomUUID();
		private String productName;
		private Category category;
		private long price;
		private long quantity;
		private String description;
		private LocalDateTime createdAt = LocalDateTime.now();
		private LocalDateTime updatedAt = LocalDateTime.now();

		@Builder
		public Create(String productName, Category category, long price, long quantity, String description) {
			this.productName = productName;
			this.category = category;
			this.price = price;
			this.quantity = quantity;
			this.description = description;
		}

		public void createId() {
			this.productId = UUID.randomUUID();
		}
	}

	@Builder
	public record ResponseDto(UUID productId, String productName, Category category, long price, long quantity,
							  String description
			, LocalDateTime createdAt, LocalDateTime updatedAt) {
	}

	@Builder
	public record Update(UUID productId, String productName, Category category, long price, long quantity,
						 String description) {
	}

}
