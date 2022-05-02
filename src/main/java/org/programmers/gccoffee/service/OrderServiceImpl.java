package org.programmers.gccoffee.service;

import lombok.RequiredArgsConstructor;
import org.programmers.gccoffee.model.Email;
import org.programmers.gccoffee.model.Order;
import org.programmers.gccoffee.model.OrderItem;
import org.programmers.gccoffee.model.OrderStatus;
import org.programmers.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Email email, String address, String postCode, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), email, address, postCode, orderItems, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order);
        return order;
    }
}
