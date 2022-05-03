package com.dojinyou.devcourse.gccoffee.service;

import com.dojinyou.devcourse.gccoffee.model.Email;
import com.dojinyou.devcourse.gccoffee.model.Order;
import com.dojinyou.devcourse.gccoffee.model.OrderItem;
import com.dojinyou.devcourse.gccoffee.model.OrderStatus;
import com.dojinyou.devcourse.gccoffee.repository.OrderRepsotiory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {
    private final OrderRepsotiory orderRepsotiory;

    public DefaultOrderService(OrderRepsotiory orderRepsotiory) {
        this.orderRepsotiory = orderRepsotiory;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(),
                                email,
                                address,
                                postcode,
                                orderItems,
                                OrderStatus.ACCEPTED,
                                LocalDateTime.now(),
                                LocalDateTime.now());
        return orderRepsotiory.insert(order);
    }
}
