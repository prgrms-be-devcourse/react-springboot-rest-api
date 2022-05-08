package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.utils.JdbcUtils;
import com.prgrammers.clone.utils.TranslatorUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<OrderItem> findByOrderId(List<UUID> orderIds) {

		String sql = String.format("select * from order_items where order_id in (%s) order by created_at desc",
				JdbcUtils.consistParameterWords(orderIds.size()));

		return jdbcTemplate.query(
				sql, JdbcUtils.buildParameters(new ArrayList<>(orderIds)), ORDER_ITEM_ROW_MAPPER
		);
	}

	@Override
	public List<OrderItem> findByOrderId(UUID orderId) {
		return jdbcTemplate.query(
				"select * from order_items where order_id = UUID_TO_BIN(:orderId)"
				, Collections.singletonMap("orderId", orderId.toString().getBytes())
				, ORDER_ITEM_ROW_MAPPER
		);
	}

	@Override
	public void deleteBy(Long orderItemId) {
		jdbcTemplate.update(
				"delete from order_items where order_item_id :=orderItemId",
				Collections.singletonMap("orderItemId", orderItemId)
		);
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(
				"delete from order_items",
				Collections.emptyMap()
		);
	}

	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (resultSet, rowNumber) -> {
		long orderItemId = resultSet.getLong("order_item_id");
		UUID orderId = TranslatorUtils.toUUID(resultSet.getBytes("order_id"));
		UUID productId = TranslatorUtils.toUUID(resultSet.getBytes("product_id"));
		Category category = Category.valueOf(resultSet.getString("category"));
		long price = resultSet.getLong("price");
		long quantity = resultSet.getLong("quantity");
		LocalDateTime createdAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("updated_at"));

		return OrderItem.builder()
				.orderItemId(orderItemId)
				.orderId(orderId)
				.productId(productId)
				.category(category)
				.price(price)
				.quantity(quantity)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.build();
	};
}
