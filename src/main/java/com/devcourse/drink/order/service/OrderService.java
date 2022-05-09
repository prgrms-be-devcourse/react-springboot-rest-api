package com.devcourse.drink.order.service;

import com.devcourse.drink.order.model.Email;
import com.devcourse.drink.order.model.Order;
import com.devcourse.drink.order.model.OrderItem;
import com.devcourse.drink.order.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

    List<Order> getAllOrder();

    Order updateOrder(UUID orderId, String address, String postcode, OrderStatus orderStatus);

    void deleteOrder(UUID orderId);
}
