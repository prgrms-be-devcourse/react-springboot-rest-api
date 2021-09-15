package com.programmers.coffeeorder.service;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import com.programmers.coffeeorder.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public class CoffeeOrderService implements OrderService {
    @Override
    public Order.DTO submitOrder(Object submit) {
        return null;
    }

    @Override
    public List<? extends Order.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to) {
        throw new UnsupportedOperationException("Listing orders not supported yet.");
    }
}
