package com.gccoffee.controller.api;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import com.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RestProductController {
    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/v1/products")
    public List<Product> productList(@RequestParam Optional<Category> category) {
        return category
            .map(productService::getProductsByCategory)
            .orElse(productService.getAllProducts());
    }

}
