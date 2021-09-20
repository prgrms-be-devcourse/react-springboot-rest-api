package com.programmers.coffeeorder.service.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.repository.delivery.CoffeeOrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicCoffeeDeliveryService implements CoffeeDeliveryService {

    private final CoffeeOrderDeliveryRepository repository;

    @Override
    public Map<String, List<CoffeeOrder.DTO>> listAppointedDeliveries(LocalDate date) {
        Map<String, List<CoffeeOrder>> deliveries = repository.listReservedDeliveries(date);
        Map<String, List<CoffeeOrder.DTO>> result = new HashMap<>();
        deliveries.forEach((key, value) -> {
            List<CoffeeOrder.DTO> converted = value.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
            result.put(key, converted);
        });
        return result;
    }
}
