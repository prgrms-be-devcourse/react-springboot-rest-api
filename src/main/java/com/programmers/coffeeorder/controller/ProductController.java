package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.entity.CoffeeProduct;
import com.programmers.coffeeorder.service.product.CoffeeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CoffeeProductService coffeeProductService;

    @GetMapping
    public ResponseEntity<List<CoffeeProduct.DTO>> listCoffeeProducts() {
        return ResponseEntity.ok(coffeeProductService.listCoffeeProductsOnSale());
    }

}
