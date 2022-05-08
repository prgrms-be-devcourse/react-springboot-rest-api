package com.prgrammers.clone.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

	private final OrderService orderService;

	public List<Order> getOrderHistories(String email) {
		return orderService.getOrderHistories(new Email(email));
	}

	@Transactional
	public void cancelOrder(UUID orderId) {
		orderService.cancel(orderId);
	}
}
