package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface CoffeeOrderService {

    CoffeeOrder.DTO submitOrder(CoffeeOrder submit);

    CoffeeOrder acceptOrder(long id);

    CoffeeOrder cancelOrder(long id);

    List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to);

}
