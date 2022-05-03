package com.devcourse.drink.order.model;

import com.devcourse.drink.product.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderItemTest {
    @Test
    @DisplayName("상품의 개수가 0개 이하로 주문되었을 경우 에러 발생")
    void orderItemCountNegativeTest() {
        UUID orderItemId = UUID.randomUUID();

        assertThatExceptionOfType(IllegalArgumentException.class).
                isThrownBy(() ->  new OrderItem(orderItemId, Category.SODA, 1000, -1));

        assertThatExceptionOfType(IllegalArgumentException.class).
                isThrownBy(() ->  new OrderItem(orderItemId, Category.SODA, 1000, 0));
    }
}