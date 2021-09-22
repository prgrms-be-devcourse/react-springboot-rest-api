package com.programmers.coffeeorder.controller.api;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CoffeeOrderService coffeeOrderService;

    @PostMapping
    public ResponseEntity<CoffeeOrder.DTO> submitOrder(@RequestBody CoffeeOrderSubmit submit) {
        String email = submit.getEmail();
        String address = submit.getAddress();
        int postcode = submit.getPostcode();
        List<CoffeeProductOrderItem> products = submit.getOrderItems().stream()
                .map(coffeeOrderDetails ->
                        new CoffeeProductOrderItem(
                                coffeeOrderDetails.getQuantity(),
                                new CoffeeProduct(coffeeOrderDetails.getProductId())))
                .collect(Collectors.toList());
        CoffeeOrder.DTO dto = coffeeOrderService.submitOrder(new CoffeeOrder(null, email, address, postcode, products));
        if (dto.getId() < 1) return ResponseEntity.internalServerError().body(dto);
        return ResponseEntity.ok(dto);
    }
}
