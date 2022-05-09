package com.devcourse.drink.product.controller.api;

import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;
import com.devcourse.drink.product.service.ProductService;
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
                .map(productService::getProductByCategory)
                .orElse(productService.getAllProduct());
    }
}
