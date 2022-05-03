package com.example.gccoffeemanagement.controller.api;

import com.example.gccoffeemanagement.controller.CreateOrderRequest;
import com.example.gccoffeemanagement.model.Email;
import com.example.gccoffeemanagement.model.Order;
import com.example.gccoffeemanagement.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(
                new Email(request.email()),
                request.address(),
                request.postcode(),
                request.orderItems()
        );
    }
}
