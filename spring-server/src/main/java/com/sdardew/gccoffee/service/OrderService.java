package com.sdardew.gccoffee.service;

import com.sdardew.gccoffee.model.Email;
import com.sdardew.gccoffee.model.Order;
import com.sdardew.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
  Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
