package com.prgrammers.clone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public String renderProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "product-list";
	}

	@GetMapping("new-product")
	public String renderNewProduct() {
		return "new-product";
	}

	@PostMapping("/products")
	public String createProduct(CreateProduct createProduct) {
		productService.create(
				createProduct.productName(),
				createProduct.category(),
				createProduct.price(), createProduct.description());

		return "redirect:/products";
	}
}
