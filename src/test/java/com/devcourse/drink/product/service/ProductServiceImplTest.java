package com.devcourse.drink.product.service;

import com.devcourse.drink.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    @DisplayName("상품을 추가합니다.")
    void productCreateTest() {

    }

    @Test
    @DisplayName("ID를 기준으로 상품을 삭제합니다.")
    void productDeleteByIdTest() {

    }

    @Test
    @DisplayName("전체 상품을 조회합니다.")
    void productFindAllTest() {

    }

    @Test
    @DisplayName("특정 카테고리 상품을 조회합니다.")
    void productFindByCategoryTest() {

    }
}