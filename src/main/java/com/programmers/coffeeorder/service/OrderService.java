package com.programmers.coffeeorder.service;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order.DTO> submitOrder(Object submit);

    List<? extends Order.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

    List<? extends Order.DTO> getDeliveryReservedOrders(LocalDate date);
}
