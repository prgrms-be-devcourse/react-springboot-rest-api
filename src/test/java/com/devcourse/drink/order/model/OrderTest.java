package com.devcourse.drink.order.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.devcourse.drink.product.model.Category.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    private static UUID orderId;
    private static Email email;
    private static String address;
    private static String postcode;
    private static List<OrderItem> orderItems;
    private static OrderStatus orderStatus;
    private static LocalDateTime now;

    @BeforeAll
    static void beforeAll() {
        orderId = UUID.randomUUID();
        email = new Email("test@test.com");
        address = "xx시 xx구 xx동 xxx-xxx";
        postcode = "00000";
        orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(UUID.randomUUID(), SODA, 1000, 1));
        orderItems.add(new OrderItem(UUID.randomUUID(), SPORTS, 1500, 1));
        orderItems.add(new OrderItem(UUID.randomUUID(), FRUIT, 2000, 1));
        orderStatus = OrderStatus.ACCEPTED;
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("주문을 생성자로 생성하는 부분 테스트")
    void productConstructorTest() {
        Order order = new Order(orderId, email, address, postcode, orderItems, orderStatus, now);

        assertThat(order.getOrderId()).isEqualTo(orderId);
        assertThat(order.getEmail()).isEqualTo(email);
        assertThat(order.getAddress()).isEqualTo(address);
        assertThat(order.getPostcode()).isEqualTo(postcode);
        assertThat(order.getOrderItems()).isEqualTo(orderItems);
        assertThat(order.getCreatedAt()).isEqualTo(now);
        assertThat(order.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("주문의 내용을 변경시 업데이트 시간이 수정되는지 확인")
    void setValueModifyUpdatedAtTest() {
        Order order = new Order(orderId, email, address, postcode, orderItems, orderStatus, now);
        LocalDateTime updatedAt = order.getUpdatedAt();

        order.setAddress("xx시 xx구 xxx로 xxx");
        assertThat(updatedAt).isNotEqualTo(order.getUpdatedAt());
        updatedAt = order.getUpdatedAt();

        order.setPostcode("11111");
        assertThat(updatedAt).isNotEqualTo(order.getUpdatedAt());
        updatedAt = order.getUpdatedAt();

        order.setOrderStatus(OrderStatus.PAYMENT_CONFIRMED);
        assertThat(updatedAt).isNotEqualTo(order.getUpdatedAt());
        updatedAt = order.getUpdatedAt();
    }
}