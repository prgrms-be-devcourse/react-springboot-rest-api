package com.prgrammers.clone.service;

import java.util.List;

import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;

public interface OrderService {

	Order createOrder(Email email,String address, String postcode, List<OrderItem> orderItems
	);
}
