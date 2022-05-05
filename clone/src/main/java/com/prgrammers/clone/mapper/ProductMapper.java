package com.prgrammers.clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.prgrammers.clone.dto.ProductDto;
import com.prgrammers.clone.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	Product createDtoToProduct(ProductDto.Create createDto);

	ProductDto.ResponseDto productToResponseDto(Product product);

	Product updateDtoToProduct(ProductDto.Update updateDto);
}
