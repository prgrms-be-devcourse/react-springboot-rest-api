package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Product {
	private final UUID productId;
	private String productName;
	private Category category;
	private long price;
	private long quantity;
	private String description;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public Product(UUID productId, String productName, Category category, long price, long quantity,
			String description) {
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Product(UUID productId, String productName, Category category, long price, long quantity, String description,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private void setProductName(String productName) {
		this.productName = productName;
		this.updatedAt = LocalDateTime.now();
	}

	private void setCategory(Category category) {
		this.category = category;
	}

	private void setPrice(long price) {
		this.price = price;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Product update(Product todo) {
		this.setProductName(todo.getProductName());
		this.setCategory(todo.getCategory());
		this.setPrice(todo.getPrice());
		this.setDescription(todo.getDescription());
		this.setQuantity(todo.getQuantity());
		this.updatedAt = LocalDateTime.now();
		return this;
	}

}
