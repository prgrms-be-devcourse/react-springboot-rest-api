package org.programmers.gccoffee.service;

import org.programmers.gccoffee.model.Email;
import org.programmers.gccoffee.model.Order;
import org.programmers.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postCode, List<OrderItem> orderItems);
}
