package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CoffeeOrderService {

    CoffeeOrder.DTO submitOrder(CoffeeOrder submit);

    Optional<CoffeeOrder.DTO> readOrder(long id);

    void acceptOrder(long id);

    void cancelOrder(long id);

    void updateOrderInfo(long id, CoffeeOrder updatedCoffeeOrder);

    void updateOrderItemsQuantity(long id, Map<Long, Integer> quantityMap);

    List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

}
