package com.devcourse.drink.order.model;

import com.devcourse.drink.product.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    @DisplayName("주문 상태에 주문 상태를 나타내는 값이 들어가 있는지 확인하는 테스트")
    void orderStatusValueTest() {
        String[] kinds = {
                "주문완료",
                "결제승인",
                "배송준비",
                "배송중",
                "배송완료",
                "주문취소"
        };

        assertThat(Arrays.stream(OrderStatus.values()).map(OrderStatus::getValue)).containsOnly(kinds);
    }
}