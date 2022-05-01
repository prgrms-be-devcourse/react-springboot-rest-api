package com.prgrammers.clone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.service.ProductService;

@Controller
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
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
		log.info("createProduct -> {}",createProduct);
		productService.create(
				createProduct.productName(),
				createProduct.category(),
				createProduct.price(), createProduct.description());

		return "redirect:/products";
	}
}
