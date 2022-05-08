package com.example.gccoffeemanagement.service;

import com.example.gccoffeemanagement.model.Email;
import com.example.gccoffeemanagement.model.Order;
import com.example.gccoffeemanagement.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
