package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;

@Repository
public class OrderReposiotryImpl implements OrderRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public OrderReposiotryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public Order insert(Order order) {
		int update = jdbcTemplate.update(
				"INSERT INTO orders(order_id,email,address,postcode,order_status,created_at,updated_at)"
						+ " VALUES (UUID_TO_BIN(:orderId), :email, :address, :postCode, :orderStatus, : createdAt, :updatedAt)"
				, toOrderParamMap(order)
		);

		order.getOrderItems()
				.forEach(item -> {
					int orderItemUpdate = jdbcTemplate.update(
							"INSERT INTO order_items(order_id, product_id, category, price, quantity, createdAt, updatedAt)"
									+ " VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
							toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item));

					if (orderItemUpdate != 1) {
						throw new RuntimeException("orderItem not exe query....");
					}
				});

		if (update != 1) {
			throw new RuntimeException("Order not exe query....");
		}

		return order;
	}

	private Map<String, Object> toOrderParamMap(Order order) {
		HashMap<String, Object> orders = new HashMap<>();
		orders.put("orderId", order.getOrderId().toString().getBytes());
		orders.put("email", order.getEmail().getAddress());
		orders.put("address", order.getAddress());
		orders.put("postcode", order.getPostCOde());
		orders.put("orderStatus", order.getOrderStatus().toString());
		orders.put("createdAt", order.getCreatedAt());
		orders.put("updatedAt", order.getUpdatedAt());

		return orders;
	}

	private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime orderCreatedAt,
			LocalDateTime orderUpdatedAt, OrderItem item) {
		HashMap<String, Object> orderItems = new HashMap<>();
		orderItems.put("orderId", orderId.toString().getBytes());
		orderItems.put("productId", item.productId().toString().getBytes());
		orderItems.put("category", item.category());
		orderItems.put("price", item.price());
		orderItems.put("quantity", item.quantity());
		orderItems.put("createdAt", orderCreatedAt);
		orderItems.put("updatedAt", orderUpdatedAt);

		return orderItems;
	}
}
