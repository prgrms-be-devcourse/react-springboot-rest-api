package org.programmers.gccoffee.controller.api;

import lombok.RequiredArgsConstructor;
import org.programmers.gccoffee.model.Category;
import org.programmers.gccoffee.model.Product;
import org.programmers.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/api/v1/products")
    public List<Product> productList(@RequestParam Optional<Category> category) {
        return category
                .map(productService::getProductsByCategory)
                .orElseGet(productService::getAllProducts);
    }
}
