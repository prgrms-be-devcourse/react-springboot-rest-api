package com.example.reactspringbootrestapi.repository;

import com.example.reactspringbootrestapi.model.Order;

public interface OrderRepository {
    Order insert(Order order);
}
