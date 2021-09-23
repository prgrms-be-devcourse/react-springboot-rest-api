package com.programmers.coffeeorder.service.delivery;

import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CoffeeDeliveryService {

    Map<String, List<CoffeeOrder.DTO>> listAppointedDeliveries(LocalDate date);

    Optional<CoffeeOrderDelivery.DTO> readCoffeeOrderDelivery(long deliveryId);

    List<CoffeeOrderDelivery.DTO> listCoffeeOrderDeliveries(LocalDateTime from, LocalDateTime to);

    CoffeeOrderDelivery.DTO createCoffeeOrderDelivery(long orderId, CoffeeOrderDelivery delivery);

}
