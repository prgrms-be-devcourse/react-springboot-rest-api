package com.example.gccoffee.contoroller;

import com.example.gccoffee.model.CreateProductRequest;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productPage(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "product-list";
    }

    @GetMapping("new-product")
    public String newProduct(){
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(CreateProductRequest createProductRequest){
        productService.createProduct(createProductRequest.productName(),
                createProductRequest.category(),
                createProductRequest.price(),
                createProductRequest.description());
        return "redirect:/products";
    }

}
