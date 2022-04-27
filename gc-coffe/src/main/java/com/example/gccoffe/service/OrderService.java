package com.example.gccoffe.service;

import com.example.gccoffe.model.Email;
import com.example.gccoffe.model.Order;
import com.example.gccoffe.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
