package org.programmers.gccoffee.controller.api;

import lombok.RequiredArgsConstructor;
import org.programmers.gccoffee.controller.CreateOrderRequest;
import org.programmers.gccoffee.model.Email;
import org.programmers.gccoffee.model.Order;
import org.programmers.gccoffee.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(
                new Email(orderRequest.email()), orderRequest.address(), orderRequest.postcode(), orderRequest.orderItems());
    }
}
