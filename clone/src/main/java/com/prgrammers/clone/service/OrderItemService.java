package com.prgrammers.clone.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderItemService {
	private final OrderItemRepository orderItemRepository;

	public List<OrderItem> getOrderItems(List<UUID> orderIds) {
		return orderItemRepository.findByOrderId(orderIds);
	}
}
