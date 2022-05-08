package com.prgrammers.clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
	OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

}
