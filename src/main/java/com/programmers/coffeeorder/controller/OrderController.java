package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @PostMapping
    public ResponseEntity<Object> submitOrder(CoffeeOrderSubmit submit) {
        return null;
    }
}
