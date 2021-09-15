package com.programmers.coffeeorder.service;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order.DTO submitOrder(Object submit);

    List<? extends Order.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

}
