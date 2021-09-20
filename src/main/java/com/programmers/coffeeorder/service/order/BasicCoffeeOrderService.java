package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.repository.order.CoffeeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicCoffeeOrderService implements CoffeeOrderService {

    private final CoffeeOrderRepository coffeeOrderRepository;

    @Override
    public CoffeeOrder.DTO submitOrder(CoffeeOrder submit) {
        CoffeeOrder order = coffeeOrderRepository.createOrder(submit);
        return order.toDTO();
    }

    @Override
    public List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to) {
        Collection<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listOrdersBetween(from, to);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

}
