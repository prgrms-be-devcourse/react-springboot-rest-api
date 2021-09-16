package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.repository.order.CoffeeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicCoffeeOrderService implements CoffeeOrderService {

    private final CoffeeOrderRepository coffeeOrderRepository;

    @Override
    public Optional<CoffeeOrder.DTO> submitOrder(CoffeeOrder submit) {
        CoffeeOrder order = coffeeOrderRepository.createOrder(submit);
        return Optional.ofNullable(order.toDTO());
    }

    @Override
    public List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to) {
        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listOrdersBetween(from, to);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoffeeOrder.DTO> getDeliveryReservedOrders(LocalDate date) {
        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listDeliveryReservedOrders(date);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

}
