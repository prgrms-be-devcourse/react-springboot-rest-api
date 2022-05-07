package com.prgrammers.clone.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
			List<OrderItem> orderItems = createDto.getOrderItems().stream()
					.map(createOrderItem ->
							OrderItem.builder()
									.category(createOrderItem.getCategory())
									.price(createOrderItem.getPrice())
									.quantity(createOrderItem.getQuantity())
									.productId(createOrderItem.getProductId())
									.build())
					.toList();

			Email email = new Email(createDto.getEmail());

			return Order.builder()
					.orderId(UUID.randomUUID())
					.email(email)
					.address(createDto.getAddress())
					.postcode(createDto.getPostcode())
					.orderStatus(createDto.getOrderStatus())
					.orderItems(orderItems)
					.createdAt(createDto.getCreatedAt())
					.updatedAt(createDto.getUpdatedAt())
					.build();
		};
	}

	public Converter<Order, OrderDto.Response> domainToResponse() {
		return order -> {
			List<OrderDto.ResponseOrderItem> responseOrderItems = order.getOrderItems().stream()
					.map(orderItem ->
							OrderDto.ResponseOrderItem
							.builder()
							.productId(orderItem.productId())
							.category(orderItem.category())
							.price(orderItem.price())
							.quantity(orderItem.quantity())
							.summary(orderItem.getTotalPrice())
							.build())
					.toList();

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
