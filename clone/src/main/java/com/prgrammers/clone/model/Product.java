package com.prgrammers.clone.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrammers.clone.exception.DomainException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PRIVATE)
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

	/**
	 * todo : RowMappper 전용 생성자
	 */
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

	public Product updateInformation(Product todo) {
		this.setProductName(todo.getProductName());
		this.setCategory(todo.getCategory());
		this.setPrice(todo.getPrice());
		this.setDescription(todo.getDescription());
		this.setQuantity(todo.getQuantity());
		this.updatedAt = LocalDateTime.now();
		return this;
	}

	public long reduce(long requestOrder) {
		if (requestOrder < 0) {
			throw new DomainException.InventoryException("요청한 재고량이 음수 값입니다.");
		}

		long restQuantity = this.quantity - requestOrder;

		if (restQuantity < 0) {
			throw new DomainException.InventoryException("재고량 보다 더 많은 개수를 요청했습니다.");
		}

		this.quantity = restQuantity;

		return this.quantity;
	}

}
