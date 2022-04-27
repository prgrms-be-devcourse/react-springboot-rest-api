package com.devcourse.gccoffee.controller.api;

import com.devcourse.gccoffee.controller.CreateOrderRequest;
import com.devcourse.gccoffee.model.Email;
import com.devcourse.gccoffee.model.Order;
import com.devcourse.gccoffee.service.OrderService;
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
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        System.out.println("이메일:"+orderRequest.email());
        return orderService.crateOrder(
                new Email(orderRequest.email()),
                orderRequest.email(),
                orderRequest.postcode(),
                orderRequest.orderItems()
        );
    }
}
