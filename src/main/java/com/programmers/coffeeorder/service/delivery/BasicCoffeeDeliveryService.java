package com.programmers.coffeeorder.service.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.repository.delivery.CoffeeOrderDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasicCoffeeDeliveryService implements CoffeeDeliveryService {

    private final CoffeeOrderDeliveryRepository repository;

    @Override
    public Map<String, List<CoffeeOrder>> listAppointedDeliveries(LocalDate date) {
        return repository.listReservedDeliveries(date);
    }
}
