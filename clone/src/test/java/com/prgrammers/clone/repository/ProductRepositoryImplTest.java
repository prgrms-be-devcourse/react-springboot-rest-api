package com.prgrammers.clone.repository;

import java.util.List;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryImplTest {

	@Autowired
	private ProductRepository productRepository;
	private Product product;

	@AfterAll
	public void finish() {
		productRepository.deleteAll();
	}

	@Order(0)
	@Test
	void testBeans() {
		Assert.notNull(productRepository, "dependency check ...");
	}

	@Order(1)
	@DisplayName("데이터 삽입")
	@Test
	void insert() {
		// given
		product = new Product(UUID.randomUUID(), "new-pack", Category.COFFEE_BEAN_PACKAGE, 1000L);
		// when
		Product insert = productRepository.insert(product);
		// then
		MatcherAssert.assertThat(insert, Matchers.samePropertyValuesAs(product));
	}

	@Order(2)
	@DisplayName("전체 데이터 조회")
	@Test
	void findAll() {
		// given

		// when
		List<Product> products = productRepository.findAll();
		// then
		Assertions.assertEquals(products.size(), 1);
	}

	@Order(3)
	@DisplayName("id 조회")
	@Test
	void findById() {
		// given
		// when
		Product findingProduct = productRepository.findById(product.getProductId())
				.orElseThrow(() -> new RuntimeException("not found resources"));
		// then

		MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
	}

	@Order(4)
	@DisplayName("이름 별 조회")
	@Test
	void findByName() {
		// given
		// when
		List<Product> products = productRepository.findByName(product.getProductName());

		// then
		MatcherAssert.assertThat(products.size(), Matchers.is(1));
		products.forEach(findingProduct -> {
			MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
		});
	}

	@Order(5)
	@DisplayName("category 조회")
	@Test
	void findByCategory() {

		// given
		// when
		List<Product> products=productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
		// then
		MatcherAssert.assertThat(products.size(),Matchers.is(1));
		products.forEach(findingProduct -> {
			MatcherAssert.assertThat(findingProduct, Matchers.samePropertyValuesAs(product));
		});
	}

	@Order(6)
	@DisplayName("상품 갱신")
	@Test
	void update() {
		//given
		String updatedName = "update-test";
		product.setProductName(updatedName);
		//when
		Product update = productRepository.update(product);
		//then
		MatcherAssert.assertThat(product.getProductName(),Matchers.is(update.getProductName()));
		MatcherAssert.assertThat(product.getProductId(),Matchers.is(update.getProductId()));

	}

}