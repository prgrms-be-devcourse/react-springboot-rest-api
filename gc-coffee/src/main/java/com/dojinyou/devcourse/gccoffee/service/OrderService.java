package com.dojinyou.devcourse.gccoffee.service;

import com.dojinyou.devcourse.gccoffee.model.Email;
import com.dojinyou.devcourse.gccoffee.model.Order;
import com.dojinyou.devcourse.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
