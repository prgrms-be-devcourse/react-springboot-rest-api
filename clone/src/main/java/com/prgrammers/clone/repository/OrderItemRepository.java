package com.prgrammers.clone.repository;

import java.util.List;
import java.util.UUID;

import com.prgrammers.clone.model.OrderItem;

public interface OrderItemRepository {
	List<OrderItem> findByOrderId(List<UUID> orderIds);

	void deleteBy(Long orderItemId);

	void deleteAll();
}
