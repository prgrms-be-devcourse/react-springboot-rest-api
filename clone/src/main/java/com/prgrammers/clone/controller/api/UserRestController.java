package com.prgrammers.clone.controller.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prgrammers.clone.dto.UserDto;
import com.prgrammers.clone.mapper.UserMapper;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.service.UserService;
import com.prgrammers.clone.utils.RegexUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserRestController {

	private final UserService userService;
	private final UserMapper userMapper;

	// todo paging
	@GetMapping("/orders")
	public ResponseEntity<List<UserDto.UserOrderResponse>> getOrders(
			@Validated
			@Email(regexp = RegexUtils.EMAIL_REGEX, message = "4-50자 안의 이메일 형식이여야 합니다.")
			@RequestParam("email") String email) {
		List<Order> orderHistories = userService.getOrderHistories(email);

		if (orderHistories.isEmpty()) {
			return ResponseEntity.ok(null);
		}

		List<UserDto.UserOrderResponse> userOrderResponses = orderHistories.stream()
				.map(order -> {
					List<UserDto.UserOrderItemResponse> userOrderItemResponses = order.getOrderItems().stream()
							.map(userMapper::orderItemToUserOrderItemResponse)
							.toList();
					UserDto.UserOrderResponse userOrderResponse = userMapper.orderToUsesrOrderResponse(order);
					userOrderResponse.addUserOrderItemResponse(userOrderItemResponses);

					return userOrderResponse;
				}).toList();

		return ResponseEntity.ok(userOrderResponses);
	}

	@DeleteMapping("/orders/{order_id}")
	public ResponseEntity<String> cancel(@PathVariable("order_id") UUID orderId) {
		userService.cancelOrder(orderId);

		return ResponseEntity.ok("취소가 완료되었습니다.");
	}

}
