package com.gccoffee.service;

import com.gccoffee.model.Email;
import com.gccoffee.model.Order;
import com.gccoffee.model.OrderItem;
import com.gccoffee.model.OrderStatus;
import com.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class SimpleOrderService implements OrderService {
    private final OrderRepository orderRepository;

    public SimpleOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(
            UUID.randomUUID(),
            email,
            address,
            postcode,
            orderItems,
            OrderStatus.ACCEPTED,
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return orderRepository.insert(order);
    }
}
