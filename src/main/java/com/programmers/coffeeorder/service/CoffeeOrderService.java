package com.programmers.coffeeorder.service;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.Order;
import com.programmers.coffeeorder.repository.CoffeeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoffeeOrderService implements OrderService {

    private final CoffeeOrderRepository coffeeOrderRepository;

    @Override
    public Optional<Order.DTO> submitOrder(Object submit) {
        if(!(submit instanceof CoffeeOrder)) return Optional.empty();
        CoffeeOrder request = (CoffeeOrder) submit;
        CoffeeOrder order = coffeeOrderRepository.createOrder(request);
        return Optional.ofNullable(order.toDTO());
    }

    @Override
    public List<? extends Order.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to) {
        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listOrdersBetween(from, to);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<? extends Order.DTO> getDeliveryReservedOrders(LocalDate date) {
        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listDeliveryReservedOrders(date);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

}
