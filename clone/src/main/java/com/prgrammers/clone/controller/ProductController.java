package com.prgrammers.clone.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.prgrammers.clone.dto.ProductDto;
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

	@GetMapping("/new-product")
	public String renderNewProduct() {
		return "new-product";
	}

	@PostMapping("/products")
	public String createProduct(ProductDto.Create createProduct) {
		log.info("createProduct -> {}", createProduct);
		productService.create(
				Product.builder()
						.productId(UUID.randomUUID())
						.price(createProduct.getPrice())
						.quantity(createProduct.getQuantity())
						.category(createProduct.getCategory())
						.productName(createProduct.getProductName())
						.description(createProduct.getDescription())
						.createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now())
						.build());

		return "redirect:/products";
	}
}
