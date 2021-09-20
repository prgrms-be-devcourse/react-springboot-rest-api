package com.programmers.coffeeorder.service.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CoffeeDeliveryService {
    Map<String, List<CoffeeOrder.DTO>> listAppointedDeliveries(LocalDate date);
}
