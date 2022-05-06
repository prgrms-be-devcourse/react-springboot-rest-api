package com.prgrammers.clone.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.prgrammers.clone.dto.OrderDto;
import com.prgrammers.clone.model.Email;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;
import com.prgrammers.clone.model.OrderStatus;

@Component
public class OrderConverter {

	public Converter<OrderDto.Create, Order> createDtoToDomain() {
		return createDto -> {
			List<OrderItem> orderItems = createDto.orderItems().stream()
					.map(createOrderItem ->
							OrderItem.builder()
									.category(createOrderItem.category())
									.price(createOrderItem.price())
									.quantity(createOrderItem.quantity())
									.productId(createOrderItem.productId())
									.build())
					.toList();

			Email email = new Email(createDto.email());

			return Order.createOrder(email,
					createDto.address(),
					createDto.postcode(),
					orderItems,
					OrderStatus.ACCEPT);

		};
	}

	public Converter<Order, OrderDto.Response> domainToResponse() {
		return order -> {
			List<OrderDto.ResponseOrderItem> responseOrderItems = order.getOrderItems().stream()
					.map(orderItem -> {
						return OrderDto.ResponseOrderItem
								.builder()
								.productId(orderItem.productId())
								.category(orderItem.category())
								.price(orderItem.price())
								.quantity(orderItem.quantity())
								.summary(orderItem.getTotalPrice())
								.build();
					}).toList();

			return OrderDto.Response
					.builder()
					.email(order.getEmail().getAddress())
					.address(order.getAddress())
					.postcode(order.getPostcode())
					.orderStatus(order.getOrderStatus())
					.orderItems(responseOrderItems)
					.totalPrice(order.calculateTotalPrice())
					.build();
		};
	}
}
