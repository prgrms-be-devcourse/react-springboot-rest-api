package com.prgrammers.clone.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;

public interface OrderRepository {
	Order insert(Order order);

	List<Order> findByEmail(Email email);

	void deleteBy(UUID orderId);

	void deleteAll();

	Optional<Order> findById(UUID orderId);

	Order update(Order order);
}
