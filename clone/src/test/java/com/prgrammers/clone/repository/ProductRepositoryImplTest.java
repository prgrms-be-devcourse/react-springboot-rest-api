package com.prgrammers.clone.repository;

import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ProductRepositoryImplTest {

	// static EmbeddedMysql embeddedMysql;
	//
	// @BeforeAll
	// public void init() {
	// 	MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(Version.v8_0_11)
	// 			.withCharset(Charset.UTF8)
	// 			.withPort(2215)
	// 			.withUser("test", "test1234!")
	// 			.withTimeZone("Asia/Seoul")
	// 			.build();
	//
	// 	embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqldConfig)
	// 			.addSchema("test-order_mgmt", ScriptResolver.classPathScripts("schema.sql"))
	// 			.start();
	//
	// }
	//
	// @AfterAll
	// public void finish() {
	// 	embeddedMysql.stop();
	// }

	@Autowired
	private ProductRepository productRepository;

	@Test
	void findAll() {
	}

	@Order(0)
	@Test
	void testBeans() {
		Assert.notNull(productRepository, "dependency check ...");
	}

	@Order(1)
	@Test
	void insert() {
		// given
		Product product = new Product(UUID.randomUUID(), "new-pack", Category.COFFEE_BEAN_PACKAGE, 1000L);
		// when
		Product insert = productRepository.insert(product);
		// then
		MatcherAssert.assertThat(insert, Matchers.samePropertyValuesAs(product));
	}

	@Test
	void update() {
	}

	@Test
	void findById() {
	}

	@Test
	void findByName() {
	}

	@Test
	void findByCategory() {
	}

	@Test
	void deleteAll() {
	}
}