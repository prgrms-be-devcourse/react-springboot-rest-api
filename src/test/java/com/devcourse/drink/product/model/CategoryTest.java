package com.devcourse.drink.product.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("카데고리에 상품 종류에 대한 값이 들어가는 있는지 확인하는 테스트")
    void categoryValueTest() {
        String[] kinds = {
                "탄산음료",
                "과일음료",
                "이온음료",
                "우유"
        };

        assertThat(Arrays.stream(Category.values()).map(Category::getValue)).containsOnly(kinds);
    }
}