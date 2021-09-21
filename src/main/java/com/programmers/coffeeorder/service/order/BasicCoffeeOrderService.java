package com.programmers.coffeeorder.service.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.repository.order.CoffeeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Optional<CoffeeOrder.DTO> readOrder(long id) {
        return coffeeOrderRepository.readOrder(id).map(CoffeeOrder::toDTO);
    }

    @Override
    public void acceptOrder(long id) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.readOrder(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Coffee order with given id not exist.");
        });

        coffeeOrder.updateOrderStatus(OrderStatus.ACCEPTED);
        coffeeOrderRepository.updateOrder(coffeeOrder);
    }

    @Override
    public void cancelOrder(long id) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.readOrder(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Coffee order with given id not exist.");
        });
        coffeeOrder.updateOrderStatus(OrderStatus.CANCELLED);
        coffeeOrderRepository.updateOrder(coffeeOrder);
    }

    @Override
    public void updateOrderInfo(long id, CoffeeOrder updatedCoffeeOrder) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.readOrder(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Order with given id not found.");
        });

        coffeeOrder.changeEmail(updatedCoffeeOrder.getEmail());
        coffeeOrder.changeDestination(updatedCoffeeOrder.getAddress());
        coffeeOrder.changePostcode(updatedCoffeeOrder.getPostcode());
        coffeeOrder.updateOrderStatus(updatedCoffeeOrder.getStatus());
        coffeeOrderRepository.updateOrder(coffeeOrder);
    }

    @Override
    public void updateOrderItemsQuantity(long id, Map<Long, Integer> quantityMap) {
        coffeeOrderRepository.updateOrderItemsQuantity(id, quantityMap);
    }

    @Override
    public List<CoffeeOrder.DTO> listOrdersBetweenTime(LocalDateTime from, LocalDateTime to) {
        Collection<CoffeeOrder> coffeeOrders = coffeeOrderRepository.listOrdersBetween(from, to);
        return coffeeOrders.stream().map(CoffeeOrder::toDTO).collect(Collectors.toList());
    }

}
