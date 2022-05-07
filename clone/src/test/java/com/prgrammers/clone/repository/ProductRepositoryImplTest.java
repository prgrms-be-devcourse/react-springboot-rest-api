package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import com.prgrammers.clone.config.TestJdbcConfig;
import com.prgrammers.clone.dto.ProductDto;
import com.prgrammers.clone.mapper.ProductMapper;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(TestJdbcConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryImplTest {

	@Autowired
	private ProductRepository productRepository;
	private Product product;

	@AfterAll
	public void finish() {
		productRepository.deleteAll();
	}

	@Test
	void testInjectionBeans() {
		Assert.notNull(productRepository, "dependency check ...");
	}

	@Order(5)
	@DisplayName("데이터 삽입")
	@Test
	void testInsert() {
		// given
		product = Product.builder()
				.productId(UUID.randomUUID())
				.productName("test-insert")
				.category(Category.COFFEE_PACKAGE)
				.price(3000)
				.description("test description...")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		// when
		Product insert = productRepository.insert(product);
		// then
		MatcherAssert.assertThat(insert, Matchers.samePropertyValuesAs(product));
	}

	@Order(10)
	@DisplayName("전체 데이터 조회")
	@Test
	void findAll() {
		// given

		// when
		List<Product> products = productRepository.findAll();
		// then
		Assertions.assertEquals(products.size(), 1);
	}

	@Order(20)
	@DisplayName("id 조회")
	@Test
	void testFindByUuid() {
		// given
		// when
		Product findingProduct = productRepository.findById(product.getProductId())
				.orElseThrow(() -> new RuntimeException("not found resources"));
		// then

		MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
	}

	@Order(30)
	@DisplayName("이름 별 조회")
	@Test
	void testFindByName() {
		// given
		// when

		List<Product> products = productRepository.findByName(product.getProductName());

		// then
		MatcherAssert.assertThat(products.size(), Matchers.is(1));
		products.forEach(findingProduct -> {
			MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
		});
	}

	@Order(40)
	@DisplayName("category 조회")
	@Test
	void testFindByCategory() {

		// given
		// when
		List<Product> products = productRepository.findByCategory(Category.COFFEE_PACKAGE);
		// then
		MatcherAssert.assertThat(products.size(), Matchers.is(1));
		products.forEach(findingProduct -> {
			MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
		});
	}

	@Order(50)
	@DisplayName("상품 갱신")
	@Test
	void testUpdate() {
		//given
		String updatedName = "update-test";
		Product updatingProduct = ProductMapper.INSTANCE.updateDtoToProduct(ProductDto.Update.builder()
				.productName(updatedName)
				.description(product.getDescription())
				.price(product.getPrice())
				.category(product.getCategory())
				.build());

		product.updateInformation(updatingProduct);
		//when
		Product update = productRepository.update(product);
		//then
		MatcherAssert.assertThat(product.getProductName(), Matchers.is(update.getProductName()));
		MatcherAssert.assertThat(product.getProductId(), Matchers.is(update.getProductId()));

	}

}