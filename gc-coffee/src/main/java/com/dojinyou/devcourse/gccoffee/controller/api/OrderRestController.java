package com.dojinyou.devcourse.gccoffee.controller.api;

import com.dojinyou.devcourse.gccoffee.controller.CreateOrderRequest;
import com.dojinyou.devcourse.gccoffee.model.Email;
import com.dojinyou.devcourse.gccoffee.model.Order;
import com.dojinyou.devcourse.gccoffee.service.OrderService;
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
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(new Email(createOrderRequest.email()),
                                        createOrderRequest.address(),
                                        createOrderRequest.postcode(),
                                        createOrderRequest.orderItems());
    }
}
