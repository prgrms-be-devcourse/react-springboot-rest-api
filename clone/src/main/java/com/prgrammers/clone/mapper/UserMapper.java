package com.prgrammers.clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.prgrammers.clone.dto.UserDto;
import com.prgrammers.clone.model.Order;
import com.prgrammers.clone.model.OrderItem;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDto.UserOrderResponse orderToUsesrOrderResponse(Order order);

	UserDto.UserOrderItemResponse orderItemToUserOrderItemResponse(OrderItem orderItem);

}
