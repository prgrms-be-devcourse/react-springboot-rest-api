package com.devcourse.drink.product.controller;

import com.devcourse.drink.product.model.CreateProductRequest;
import com.devcourse.drink.product.model.Product;
import com.devcourse.drink.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/products/create")
    public String createProductPage() {
        return "products/create";
    }

    @PostMapping("products/create")
    public String requestCreateProduct(CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest.name(),
                createProductRequest.category(),
                createProductRequest.price(),
                createProductRequest.description()
        );
        return "redirect:/products/index";
    }
}
