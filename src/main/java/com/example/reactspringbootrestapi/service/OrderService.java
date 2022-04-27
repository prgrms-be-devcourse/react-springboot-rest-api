package com.example.reactspringbootrestapi.service;

import com.example.reactspringbootrestapi.model.Email;
import com.example.reactspringbootrestapi.model.Order;
import com.example.reactspringbootrestapi.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
