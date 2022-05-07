package com.prgrammers.clone.repository;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.prgrammers.clone.config.TestJdbcConfig;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.Product;

@SpringJUnitConfig(TestJdbcConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryImplTest {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;
	private List<Product> products;

	@BeforeAll
	void init() {
		products = productRepository.findAll();
	}

	@org.junit.jupiter.api.Order(1)
	@DisplayName("주문 생성")
	@Test
	void insert() {
		// given
		Product buy = products.get(0);
		OrderItem orderItem = OrderItem.builder()
				.productId(buy.getProductId())
				.quantity(1)
				.price(buy.getPrice())
				.category(buy.getCategory())
				.build();

		List<OrderItem> itemForBuy = List.of(orderItem);

		// when
		Order order = Order.create(
				new Email("dev@programmers.com.kr"),
				"seoul",
				"123-12",
				itemForBuy
		);

		Order insertedOrder = orderRepository.insert(order);

		// then
		Assertions.assertThat(order).isNotNull();
		MatcherAssert.assertThat(insertedOrder, Matchers.samePropertyValuesAs(order));
	}

	@org.junit.jupiter.api.Order(1)
	@DisplayName("email(식별정보)로 주문 내역 조회")
	@Test
	void findByEmail() {
		// given

		// then
		List<Order> histories = orderRepository.findByEmail(new Email("dev@programmers.com.kr"));

		// when
		Assertions.assertThat(histories.size()).isEqualTo(4);
	}
}