package com.devcourse.drink.order.service;

import com.devcourse.drink.config.error.ErrorType;
import com.devcourse.drink.config.exception.OrderNotMatchedException;
import com.devcourse.drink.order.model.Email;
import com.devcourse.drink.order.model.Order;
import com.devcourse.drink.order.model.OrderItem;
import com.devcourse.drink.order.model.OrderStatus;
import com.devcourse.drink.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.drink.config.error.ErrorType.ORDER_NOT_MATCHED;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED, now, now);
        return orderRepository.insert(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(UUID orderId, String address, String postcode, OrderStatus orderStatus) {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            Order modifyOrder = order.get();
            modifyOrder.setAddress(address);
            modifyOrder.setPostcode(postcode);
            modifyOrder.setOrderStatus(orderStatus);
            return orderRepository.update(modifyOrder);
        } else {
            throw new OrderNotMatchedException(ORDER_NOT_MATCHED);
        }
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
