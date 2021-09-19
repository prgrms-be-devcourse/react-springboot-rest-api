package com.programmers.coffeeorder.repository.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CoffeeOrderDeliveryRepository {
    Map<String, List<CoffeeOrder>> listReservedDeliveries(LocalDate date);
}
