package com.gccoffee.service;


import com.gccoffee.model.Email;
import com.gccoffee.model.Order;
import com.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
