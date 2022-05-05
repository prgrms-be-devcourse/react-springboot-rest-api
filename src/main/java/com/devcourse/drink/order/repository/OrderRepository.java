package com.devcourse.drink.order.repository;

import com.devcourse.drink.order.model.Email;
import com.devcourse.drink.order.model.Order;
import com.devcourse.drink.order.model.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order insert(Order order);

    Order update(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID orderId);

    Optional<Order> findByEmail(Email email);

    void deleteById(UUID orderId);

}
