package com.devcourse.gccoffee.service;

import com.devcourse.gccoffee.model.Email;
import com.devcourse.gccoffee.model.Order;
import com.devcourse.gccoffee.model.OrderItem;
import com.devcourse.gccoffee.model.OrderStatus;
import com.devcourse.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOderService implements OrderService {
    private final OrderRepository orderRepository;

    public DefaultOderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order crateOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(
                UUID.randomUUID(),
                email,
                address,
                postcode,
                orderItems,
                OrderStatus.ACCEPTED,
                LocalDateTime.now(),
                LocalDateTime.now());

        return orderRepository.insert(order);
    }
}
