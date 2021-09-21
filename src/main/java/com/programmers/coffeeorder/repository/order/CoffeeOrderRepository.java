package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface CoffeeOrderRepository {

    CoffeeOrder createOrder(CoffeeOrder coffeeOrder);

    Optional<CoffeeOrder> readOrder(long id);

    CoffeeOrder updateOrder(CoffeeOrder coffeeOrder);

    void updateOrderItemsQuantity(long orderId, Map<Long, Integer> quantityMap);

    Collection<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to);

}
