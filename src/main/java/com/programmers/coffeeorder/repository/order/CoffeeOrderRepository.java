package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface CoffeeOrderRepository {

    CoffeeOrder createOrder(CoffeeOrder coffeeOrder);

    Optional<CoffeeOrder> readOrder(long id);

    Collection<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to);

}
