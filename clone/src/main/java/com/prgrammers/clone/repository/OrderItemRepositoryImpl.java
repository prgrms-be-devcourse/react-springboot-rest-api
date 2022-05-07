package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.utils.TranslatorUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<OrderItem> findByOrderId(List<UUID> orderIds) {

		String sql = String.format("select * from order_items where order_id in (%s) order by created_at desc",
				consistParameterWords(orderIds.size()));
		return jdbcTemplate.query(
				sql, buildParameters(new ArrayList<>(orderIds)), ORDER_ITEM_ROW_MAPPER
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

	private Map<String, Object> buildParameters(List<UUID> orderIdsCopy) {
		HashMap<String, Object> orderIds = new HashMap<>();
		AtomicInteger number = new AtomicInteger();
		orderIdsCopy.forEach(orderId -> {
			orderIds.put("orderId" + number.getAndIncrement(), orderId.toString().getBytes());
		});

		return orderIds;
	}

	private String consistParameterWords(int size) {
		String inQueryWords = String.join(",", Collections.nCopies(size, "UUID_TO_BIN(?)"));
		String[] inQueryParams = inQueryWords.split(",");
		AtomicInteger index = new AtomicInteger();
		return Arrays.stream(inQueryParams)
				.map(s -> s.replace("?", ":orderId" + index.getAndIncrement()))
				.collect(Collectors.joining(","));
	}
}
