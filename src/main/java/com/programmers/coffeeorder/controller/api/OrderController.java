package com.programmers.coffeeorder.controller.api;

import com.programmers.coffeeorder.controller.bind.ApiResponse;
import com.programmers.coffeeorder.controller.bind.CoffeeOrderSubmit;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.ProductOrderItem;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        CoffeeOrder.DTO dto = coffeeOrderService.submitOrder(CoffeeOrder.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .orderItems(products.stream().map(ProductOrderItem.class::cast).collect(Collectors.toList())).build());
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, exception.getLocalizedMessage()));
    }
}
