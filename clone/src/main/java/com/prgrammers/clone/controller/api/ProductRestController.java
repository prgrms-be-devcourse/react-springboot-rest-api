package com.prgrammers.clone.controller.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prgrammers.clone.dto.ProductDto;
import com.prgrammers.clone.mapper.ProductMapper;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.service.ProductService;

@RequestMapping("/api/v1/product")
@RestController
public class ProductRestController {

	private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);
	private final ProductService productService;
	private final ProductMapper mapper;

	public ProductRestController(ProductService productService, ProductMapper mapper) {
		this.productService = productService;
		this.mapper = mapper;
	}

	@GetMapping("")
	public List<Product> getProducts(@RequestParam Optional<Category> category) {
		return category
				.map(productService::getProductByCategory)
				.orElseGet(productService::getAllProducts);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<ProductDto.ResponseDto> getProduct(@PathVariable("product_id") UUID productId) {
		Product product = productService.getProduct(productId);
		ProductDto.ResponseDto responseDto = mapper.productToResponseDto(product);

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("")
	public ResponseEntity<ProductDto.ResponseDto> create(@RequestBody ProductDto.Create createProduct) {
		createProduct.createId();
		Product creatingProduct = mapper.createDtoToProduct(createProduct);
		Product product = productService.create(creatingProduct);
		ProductDto.ResponseDto responseDto = mapper.productToResponseDto(product);

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("")
	public ResponseEntity<ProductDto.ResponseDto> update(@RequestBody ProductDto.Update updateDto) {
		Product updatingProduct = mapper.updateDtoToProduct(updateDto);
		Product updatedProduct = productService.update(updatingProduct);
		ProductDto.ResponseDto responseDto = mapper.productToResponseDto(updatedProduct);

		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{product_id}")
	public ResponseEntity<String> delete(@PathVariable("product_id") UUID productId) {
		productService.delete(productId);

		return ResponseEntity.ok("delete success .. ");
	}

}
