package com.prgrammers.clone.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.repository.OrderItemRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderItemServiceTest {

	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;

	@BeforeAll
	@DisplayName("init")
	void init() {
		orderItemRepository = Mockito.mock(OrderItemRepository.class);
		orderItemService = new OrderItemService(orderItemRepository);
		//then
	}

	@Test
	@DisplayName("주문 목록에 관한 상세 내용 가져오기")
	void testGetOrderItems() {
		UUID orderId1 = UUID.randomUUID();
		UUID orderId2 = UUID.randomUUID();
		List<UUID> ids = List.of(orderId1, orderId2);
		List<OrderItem> orderItems = List.of(
				OrderItem.builder()
						.orderId(orderId1)
						.orderItemId(1L)
						.quantity(10)
						.price(200)
						.category(Category.COFFEE)
						.build(),

				OrderItem.builder()
						.orderId(orderId2)
						.orderItemId(1L)
						.quantity(5)
						.price(100)
						.category(Category.CAKE)
						.build()
		);
		BDDMockito.given(orderItemRepository.findByOrderId(ids)).willReturn(orderItems);
		List<OrderItem> orderItemBundles = orderItemService.getOrderItems(ids);
		Assertions.assertThat(orderItemBundles.size()).isEqualTo(2);
	}
}