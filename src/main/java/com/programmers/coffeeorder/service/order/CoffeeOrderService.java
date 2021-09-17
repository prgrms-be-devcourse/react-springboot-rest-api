package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.CoffeeOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CoffeeOrderService {

    CoffeeOrder.DTO submitOrder(CoffeeOrder submit);

    List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

    List<CoffeeOrder.DTO> getDeliveryReservedOrders(LocalDate date);
}
