package com.prgrammers.clone.repository;

import java.util.List;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;

public interface OrderRepository {
	Order insert(Order order);

	List<Order> findByEmail(Email email);
}
