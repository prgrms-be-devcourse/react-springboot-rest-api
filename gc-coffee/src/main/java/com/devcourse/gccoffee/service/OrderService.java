package com.devcourse.gccoffee.service;

import com.devcourse.gccoffee.model.Email;
import com.devcourse.gccoffee.model.Order;
import com.devcourse.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order crateOrder(Email email, String address, String postCode, List<OrderItem> orderItems);
}
