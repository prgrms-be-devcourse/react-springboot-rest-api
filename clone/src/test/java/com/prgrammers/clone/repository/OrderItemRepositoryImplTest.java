package com.prgrammers.clone.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.javafaker.Faker;
import com.prgrammers.clone.config.TestJdbcConfig;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.Product;

@SpringJUnitConfig(TestJdbcConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderItemRepositoryImplTest {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	private List<Order> orders;
	private List<Product> products;
	private Email email;
	private List<OrderItem> orderItems;

	@BeforeAll
	void init() {

		email = new Email("test@programmers.co.kr");
		orders = orderRepository.findByEmail(email);

		Faker faker = new Faker();
		products = Stream.generate(() -> {
					return Product.create(
							faker.commerce().productName(),
							Category.COFFEE,
							3000,
							10,
							"test..."
					);

				}).limit(3)
				.toList();

		products.forEach(product -> productRepository.insert(product));

		List<OrderItem> orderItems = products.stream()
				.map(product -> {
					return OrderItem
							.builder()
							.productId(product.getProductId())
							.category(product.getCategory())
							.price(product.getPrice())
							.quantity(product.getQuantity())
							.build();
				}).toList();

	}

	@AfterAll
	void finish() {
		orderItemRepository.deleteAll();
		orderRepository.deleteAll();
		productRepository.deleteAll();
	}

	@Test
	@DisplayName("나의 주문목록에 대한 주문 상세 내역 조회하기")
	void testFindByOrderId() {
		// given
		orderItems = products.stream()
				.map(product -> {
					return OrderItem
							.builder()
							.productId(product.getProductId())
							.category(product.getCategory())
							.price(product.getPrice())
							.quantity(product.getQuantity())
							.build();
				}).toList();

		orderRepository.insert(
				Order.create(
						email, "test", "123-123",
						orderItems
				)
		);
		//when
		List<Order> orders = orderRepository.findByEmail(email);

		List<UUID> orderIds = orders.stream()
				.map(Order::getOrderId)
				.toList();

		List<OrderItem> items = orderItemRepository.findByOrderId(orderIds);

		//then
		Assertions.assertThat(items.size()).isEqualTo(3);
		AtomicInteger index = new AtomicInteger();
		items.forEach(item -> {
			MatcherAssert.assertThat(item.productId(),
					Matchers.is(orderItems.get(index.getAndIncrement()).productId()));
		});

	}

	@Test
	@DisplayName("주문한 목록이 아무것도 없을 때")
	void testDoNotOrderInformation() {
		//given
		List<UUID> orderids = List.of();
		List<OrderItem> byOrderId = orderItemRepository.findByOrderId(orderids);
		//when

		//then
	}

}