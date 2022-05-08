package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrammers.clone.exception.JdbcException;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.OrderStatus;
import com.prgrammers.clone.utils.TranslatorUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Order insert(Order order) {
		int update = jdbcTemplate.update(
				"INSERT INTO orders(order_id,email,address,postcode,order_status,created_at,updated_at)"
						+ " VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)"
				, toOrderParamMap(order)
		);

		order.getOrderItems()
				.forEach(item -> {
					int orderItemUpdate = jdbcTemplate.update(
							"INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at)"
									+ " VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
							toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item));

					if (orderItemUpdate != 1) {
						throw new JdbcException.NotExecuteQuery("orderItem not exe query....");
					}
				});

		if (update != 1) {
			throw new JdbcException.NotExecuteQuery("Order not exe query....");
		}

		return order;
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
					"select * from orders where order_id = UUID_TO_BIN(:orderId)"
					, Collections.singletonMap("orderId", orderId.toString().getBytes())
					, ORDER_ROW_MAPPER
			));
		} catch (EmptyResultDataAccessException e) {
			throw new JdbcException.NotFoundDomain(e.getMessage());
		}
	}

	@Override
	public Order update(Order order) {
		int update = jdbcTemplate.update(
				"UPDATE orders SET order_status =:orderStatus where order_id = UUID_TO_BIN(:orderId)"
				, toOrderParamMap(order)
		);

		if (update != 1) {
			throw new JdbcException.NotExecuteQuery("order 의 update 실행문이 정상적으로 처리되지 못했습니다 .. ");
		}

		return order;
	}

	@Override
	public List<Order> findByEmail(Email email) {
		return jdbcTemplate.query(
				"select * from orders where email= :email",
				Collections.singletonMap("email", email.getAddress()),
				ORDER_ROW_MAPPER
		);
	}

	@Override
	public void deleteBy(UUID orderId) {
		jdbcTemplate.update(
				"delete from orders where order_id = :orderId"
				, Collections.singletonMap("OrderId", orderId.toString().getBytes())
		);
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(
				"delete from orders",
				Collections.emptyMap()
		);
	}

	private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, rowNumber) -> {
		UUID orderId = TranslatorUtils.toUUID(resultSet.getBytes("order_id"));
		Email email = new Email(resultSet.getString("email"));
		String address = resultSet.getString("address");
		String postcode = resultSet.getString("postcode");
		OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
		LocalDateTime createdAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("updated_at"));
		return Order.builder()
				.orderId(orderId)
				.email(email)
				.address(address)
				.postcode(postcode)
				.orderStatus(orderStatus)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.build();
	};

	private Map<String, Object> toOrderParamMap(Order order) {
		HashMap<String, Object> orders = new HashMap<>();
		orders.put("orderId", order.getOrderId().toString().getBytes());
		orders.put("email", order.getEmail().getAddress());
		orders.put("address", order.getAddress());
		orders.put("postcode", order.getPostcode());
		orders.put("orderStatus", order.getOrderStatus().toString());
		orders.put("createdAt", order.getCreatedAt());
		orders.put("updatedAt", order.getUpdatedAt());

		return orders;
	}

	private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime orderCreatedAt,
			LocalDateTime orderUpdatedAt, OrderItem orderItem) {
		HashMap<String, Object> orderItems = new HashMap<>();
		orderItems.put("orderId", orderId.toString().getBytes());
		orderItems.put("productId", orderItem.productId().toString().getBytes());
		orderItems.put("category", orderItem.category().toString());
		orderItems.put("price", orderItem.price());
		orderItems.put("quantity", orderItem.quantity());
		orderItems.put("createdAt", orderCreatedAt);
		orderItems.put("updatedAt", orderUpdatedAt);

		return orderItems;
	}
}
