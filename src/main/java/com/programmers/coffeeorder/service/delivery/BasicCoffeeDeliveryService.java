package com.programmers.coffeeorder.service.delivery;

import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.repository.delivery.CoffeeOrderDeliveryRepository;
import com.programmers.coffeeorder.repository.order.CoffeeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicCoffeeDeliveryService implements CoffeeDeliveryService {

    private final CoffeeOrderRepository coffeeOrderRepository;
    private final CoffeeOrderDeliveryRepository coffeeOrderDeliveryRepository;

    @Override
    public Map<String, List<CoffeeOrder.DTO>> listAppointedDeliveries(LocalDate date) {
        Map<String, List<CoffeeOrder>> deliveries = coffeeOrderDeliveryRepository.listReservedDeliveries(date);
        Map<String, List<CoffeeOrder.DTO>> result = new HashMap<>();
        deliveries.forEach((key, value) -> {
            List<CoffeeOrder.DTO> converted = value.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
            result.put(key, converted);
        });
        return result;
    }

    @Override
    public Optional<CoffeeOrderDelivery.DTO> readCoffeeOrderDelivery(long deliveryId) {
        return coffeeOrderDeliveryRepository.readCoffeeOrderDelivery(deliveryId).map(CoffeeOrderDelivery::toDTO);
    }

    @Override
    public CoffeeOrderDelivery.DTO createCoffeeOrderDelivery(long orderId, CoffeeOrderDelivery delivery) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.readOrder(orderId).orElseThrow(() -> {
            throw new IllegalArgumentException("Order with given id not found. Please check your input.");
        });

        if(!coffeeOrder.getStatus().equals(OrderStatus.ACCEPTED))
            throw new IllegalArgumentException("Only accepted orders can be delivered.");

        delivery.setCoffeeOrder(coffeeOrder);
        if(delivery.getSender().isEmpty()) delivery.registerSenderWithEmail();
        if(delivery.getDestination().isEmpty()) delivery.registerDestinationWithOrderInfo();

        return coffeeOrderDeliveryRepository.createCoffeeOrderDelivery(delivery).toDTO();
    }
}
