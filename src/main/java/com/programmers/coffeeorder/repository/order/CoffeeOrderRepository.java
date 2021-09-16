package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.CoffeeOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CoffeeOrderRepository {

    CoffeeOrder createOrder(CoffeeOrder coffeeOrder);

    Optional<CoffeeOrder> readOrder(long id);

    List<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to);

    List<CoffeeOrder> listDeliveryReservedOrders(LocalDate date);

}
