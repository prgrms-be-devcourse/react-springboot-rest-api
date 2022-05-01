package com.prgrammers.clone.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prgrammers.clone.controller.CreateProduct;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.service.ProductService;


@RestController
public class ProductRestController {
	private final ProductService productService;

	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/api/v1/products")
	public List<Product> getProduct(@RequestParam Optional<Category> category) {
		return category
				.map(productService::getProductByCategory)
				.orElseGet(productService::getAllProducts);
	}

	@GetMapping("/api/v2/products")
	public List<Product> getProductV2(@RequestBody CreateProduct createProduct) {
		return null;
	}
}
