package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.CoffeeProduct;
import com.programmers.coffeeorder.entity.CoffeeType;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final CoffeeOrderService coffeeOrderService;

    @PostMapping
    public ResponseEntity<CoffeeOrder.DTO> submitOrder(@RequestBody CoffeeOrderSubmit submit) {
        String email = submit.getEmail();
        String address = submit.getAddress();
        int postcode = submit.getPostcode();
        List<CoffeeProduct> products = submit.getOrderItems().stream()
                .map(coffeeOrderDetails -> new CoffeeProduct(coffeeOrderDetails.getProductId()))
                .collect(Collectors.toList());
        CoffeeOrder.DTO dto = coffeeOrderService.submitOrder(new CoffeeOrder(null, email, address, postcode, products));
        if(dto.getId() == null) return ResponseEntity.internalServerError().body(dto);
        return ResponseEntity.ok(dto);
    }
}
