package com.example.gccoffeemanagement.service;

import com.example.gccoffeemanagement.model.Email;
import com.example.gccoffeemanagement.model.Order;
import com.example.gccoffeemanagement.model.OrderItem;
import com.example.gccoffeemanagement.model.OrderStatus;
import com.example.gccoffeemanagement.repository.OrderRepository;
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
        var order = new Order(
                UUID.randomUUID(),
                email,
                address,
                postcode,
                orderItems,
                OrderStatus.ACCEPTED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return orderRepository.insert(order);
    }
}
