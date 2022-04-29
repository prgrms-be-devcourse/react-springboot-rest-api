package org.programmers.gccoffee.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.gccoffee.model.Product;
import org.programmers.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "product-list";
    }

    @GetMapping("/new-product")
    public String newProductPage() {
        return "new-product";
    }

    @PostMapping("/new-product")
    public String newProduct(CreateProductRequest productRequest) {
        productService.createProduct(productRequest.productName(),
                productRequest.category(),
                productRequest.price(),
                productRequest.description());

        return "redirect:/products";
    }
}