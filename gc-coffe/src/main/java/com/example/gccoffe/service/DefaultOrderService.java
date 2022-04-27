package com.example.gccoffe.service;

import com.example.gccoffe.model.Email;
import com.example.gccoffe.model.Order;
import com.example.gccoffe.model.OrderItem;
import com.example.gccoffe.model.OrderStatus;
import com.example.gccoffe.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        return orderRepository.insert(order);
    }
}
