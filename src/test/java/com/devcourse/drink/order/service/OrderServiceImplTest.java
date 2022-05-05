package com.devcourse.drink.order.service;

import com.devcourse.drink.product.repository.ProductRepository;
import com.devcourse.drink.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("주문을 추가합니다.")
    void orderCreateTest() {

    }

    @Test
    @DisplayName("주문 ID를 기준으로 상품을 삭제합니다.")
    void orderDeleteByIdTest() {

    }

    @Test
    @DisplayName("전체 주문을 조회합니다.")
    void orderFindAllTest() {

    }

    @Test
    @DisplayName("주문 ID를 기준으로 주문을 업데이트")
    void orderUpdateByIdTest() {

    }
}