package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CoffeeOrderService {

    CoffeeOrder.DTO submitOrder(CoffeeOrder submit);

    List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

    List<CoffeeOrder.DTO> getDeliveryReservedOrders(LocalDate date);
}
