package com.programmers.coffeeorder.repository.delivery;

import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CoffeeOrderDeliveryRepository {

    Map<String, List<CoffeeOrder>> listReservedDeliveries(LocalDate date);

    Optional<CoffeeOrderDelivery> readCoffeeOrderDelivery(long id);

    CoffeeOrderDelivery createCoffeeOrderDelivery(CoffeeOrderDelivery coffeeOrderDelivery);

}
