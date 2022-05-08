package com.prgrammers.clone.service;

import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.OrderStatus;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

	private OrderService orderService;

	private OrderRepository orderRepository;
	private ProductService productService;
	private OrderItemService orderItemService;
	private final Faker faker = new Faker();

	@BeforeAll
	void init() {
		orderRepository = Mockito.mock(OrderRepository.class);
		productService = Mockito.mock(ProductService.class);
		orderItemService = Mockito.mock(OrderItemService.class);

		orderService = new OrderService(orderRepository, productService, orderItemService);
	}

	@DisplayName("주문 생성")
	@Test
	void testCreateOrder() {
		// given
		UUID orderId = UUID.randomUUID();
		List<Product> products = getProducts();
		List<OrderItem> orderItemsForBuy = getOrderItemsForBuy(products, new AtomicInteger());

		Order requestOrder = Order.builder()
				.orderId(orderId)
				.orderItems(orderItemsForBuy)
				.orderStatus(OrderStatus.ACCEPT)
				.postcode(faker.address().zipCode())
				.address(faker.address().buildingNumber())
				.build();

		given(orderRepository.insert(requestOrder)).willReturn(requestOrder);
		given(orderRepository.insert(requestOrder)).willReturn(requestOrder);
		products.forEach(product -> {
			given(productService.getProduct(product.getProductId())).willReturn(product);
		});

		// when
		Order order = orderService.createOrder(requestOrder);
		// then
		List<Long> originalQuantity = products.stream()
				.map(Product::getQuantity)
				.toList();
		List<Long> quantityForBuy = orderItemsForBuy.stream()
				.map(OrderItem::quantity)
				.toList();

		AtomicInteger index = new AtomicInteger();
		List<Long> expectQuantity = originalQuantity.stream()
				.map(quantity -> quantity -= orderItemsForBuy.get(index.getAndIncrement()).quantity())
				.toList();

		Assertions.assertThat(order).isNotNull();

		MatcherAssert.assertThat(order, Matchers.samePropertyValuesAs(requestOrder));
		List<Long> resultQuantity = products.stream()
				.map(Product::getQuantity).toList();

		MatcherAssert.assertThat(resultQuantity, Matchers.samePropertyValuesAs(expectQuantity));

	}

	@DisplayName("사용자를 식별할 수 있는 이메일을 가지고 주문 목록 조회")
	@Test
	void testGetOrderHistories() {
		// given
		UUID orderId = UUID.randomUUID();
		Email email = new Email("dev@programmers.co.kr");

		List<Product> products = getProducts();

		AtomicInteger index = new AtomicInteger();
		List<OrderItem> orderItemsForBuy = getOrderItemsForBuy(products, index);

		Order requestOrder = Order.builder()
				.email(email)
				.orderId(orderId)
				.orderItems(orderItemsForBuy)
				.orderStatus(OrderStatus.ACCEPT)
				.postcode(faker.address().zipCode())
				.address(faker.address().buildingNumber())
				.build();

		List<Order> requestOrders = List.of(requestOrder);
		List<UUID> orderIds = requestOrders.stream()
				.map(Order::getOrderId)
				.toList();
		given(orderRepository.findByEmail(email)).willReturn(requestOrders);
		given(orderItemService.getOrderItems(orderIds)).willReturn(orderItemsForBuy);
		// when
		List<Order> orderHistories = orderService.getOrderHistories(email);
		// then
		Assertions.assertThat(orderHistories.size()).isEqualTo(1);
		MatcherAssert.assertThat(orderHistories, Matchers.samePropertyValuesAs(requestOrders));
	}

	@DisplayName("취소시 상태 변경 및 재고 증가")
	@Test
	void testCancel() {
		// given
		UUID orderId = UUID.randomUUID();
		List<Product> products = getProducts();
		List<OrderItem> orderItemsForBought = getOrderItemsForBuy(products, new AtomicInteger());
		// given
		Order alreadyOrder = Order.builder()
				.orderId(orderId)
				.orderItems(orderItemsForBought)
				.orderStatus(OrderStatus.ACCEPT)
				.postcode(faker.address().zipCode())
				.address(faker.address().buildingNumber())
				.build();

		List<UUID> productIds = products.stream()
				.map(Product::getProductId)
				.toList();
		given(orderRepository.findById(orderId)).willReturn(Optional.ofNullable(alreadyOrder));
		given(orderItemService.getOrderItems(orderId)).willReturn(orderItemsForBought);
		given(productService.getProductsByIds(productIds)).willReturn(products);

		orderService.cancel(orderId);

		AtomicInteger index = new AtomicInteger();
		List<Long> originalQuantities = products.stream()
				.map(Product::getQuantity)
				.toList();
		List<Long> redoQuantities = orderItemsForBought.stream()
				.map(OrderItem::quantity)
				.toList();
		List<Long> expectedQuantities = originalQuantities.stream()
				.map(quantity -> quantity + redoQuantities.get(index.getAndIncrement()))
				.toList();

		List<Long> resultQuantities = products.stream()
				.map(Product::getQuantity)
				.toList();

		Assertions.assertThat(alreadyOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
		MatcherAssert.assertThat(redoQuantities, Matchers.samePropertyValuesAs(expectedQuantities));

	}

	private List<OrderItem> getOrderItemsForBuy(List<Product> products, AtomicInteger index) {
		return Stream.generate(() ->
		{
			Product product = products.get(index.get());
			OrderItem orderItem = OrderItem
					.builder()
					.orderItemId(index.longValue())
					.orderId(product.getProductId())
					.category(product.getCategory())
					.productId(product.getProductId())
					.quantity(faker.number().numberBetween(10, 100))
					.price(product.getPrice())
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.build();

			index.getAndIncrement();
			return orderItem;
		}).limit(3).toList();
	}

	private List<Product> getProducts() {
		return Stream.generate(() ->
				Product.builder()
						.productId(UUID.randomUUID())
						.productName(faker.commerce().productName())
						.category(Category.COFFEE)
						.quantity(faker.number().numberBetween(1000, 2000))
						.price(3000)
						.createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now())
						.build()).limit(3).toList();
	}
}