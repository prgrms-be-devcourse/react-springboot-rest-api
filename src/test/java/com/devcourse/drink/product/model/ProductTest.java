package com.devcourse.drink.product.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.drink.product.model.Category.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductTest {

    private static UUID productId;
    private static String name;
    private static Category category;
    private static long price;
    private static String description;
    private static LocalDateTime now;

    @BeforeAll
    static void beforeAll() {
        productId = UUID.randomUUID();
        name = "test";
        category = SODA;
        price = 1000;
        description = "test";
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("상품을 생성자로 생성하는 부분 테스트")
    void productConstructorTest() {
        Product product = new Product(productId, name, category, price, description, now, now);

        assertThat(product.getProductId()).isEqualTo(productId);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getCreatedAt()).isEqualTo(now);
        assertThat(product.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("상품을 생성자를 이용하여 생성할때 가격이 음수라면 에러가 발생하는지 확인")
    void constructorPriceInvalidTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Product(productId, name, category, -1, description, now, now));
        /* 차후에 메시지까지 확인할 수 있도록 구현
        *  지금은 에러 타입 체크만            */
//        assertThat(e.getMessage()).isEqualTo(PRICE_NEGATIVE_VALUE.message());
    }

    @Test
    @DisplayName("price 값을 음수로 변경시도시 에러가 발생하는지 확인")
    void setPriceInvalidTest() {
        Product product = new Product(productId, name, category, 1000, description, now, now);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> product.setPrice(-1));
    }

    @Test
    @DisplayName("상품의 내용을 변경시 업데이트 시간이 수정되는지 확인")
    void setValueModifyUpdatedAtTest() {
        Product product = new Product(productId, name, category, 1000, description, now, now);
        LocalDateTime updatedAt = product.getUpdatedAt();

        product.setName("modify");
        assertThat(updatedAt).isNotEqualTo(product.getUpdatedAt());
        updatedAt = product.getUpdatedAt();

        product.setCategory(SPORTS);
        assertThat(updatedAt).isNotEqualTo(product.getUpdatedAt());
        updatedAt = product.getUpdatedAt();

        product.setDescription("test2");
        assertThat(updatedAt).isNotEqualTo(product.getUpdatedAt());
        updatedAt = product.getUpdatedAt();

        product.setPrice(111);
        assertThat(updatedAt).isNotEqualTo(product.getUpdatedAt());
        updatedAt = product.getUpdatedAt();
    }
}