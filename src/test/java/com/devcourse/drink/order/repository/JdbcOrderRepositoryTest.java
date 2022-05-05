package com.devcourse.drink.order.repository;

import com.devcourse.drink.config.exception.OrderNotMatchedException;
import com.devcourse.drink.order.model.Email;
import com.devcourse.drink.order.model.Order;
import com.devcourse.drink.order.model.OrderItem;
import com.devcourse.drink.order.model.OrderStatus;
import com.devcourse.drink.product.model.Category;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class JdbcOrderRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"),
                        ScriptResolver.classPathScript("data.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private Order orderCreate() {
        LocalDateTime now = LocalDateTime.now();
        List<OrderItem> orderItems = List.of(
                new OrderItem(UUID.fromString("1bbc72f4-9ab3-498e-aa03-7cfdcbc1fb61"), Category.SODA, 2000, 1),
                new OrderItem(UUID.fromString("3e91e46b-bb87-45ec-906c-bdb5eb3edab4"), Category.FRUIT, 2500, 2)
        );
        return new Order(UUID.randomUUID(),
                new Email("test@test.com"),
                "xx시 xx구 xx동",
                "00000",
                orderItems,
                OrderStatus.ACCEPTED,
                now,
                now);
    }

    private List<Order> ordersCreate() {
        LocalDateTime now = LocalDateTime.now();

        List<OrderItem> orderItems1 = List.of(
                new OrderItem(UUID.fromString("1bbc72f4-9ab3-498e-aa03-7cfdcbc1fb61"), Category.SODA, 2000, 3),
                new OrderItem(UUID.fromString("3e91e46b-bb87-45ec-906c-bdb5eb3edab4"), Category.FRUIT, 2500, 2)
        );
        List<OrderItem> orderItems2 = List.of(
                new OrderItem(UUID.fromString("1bbc72f4-9ab3-498e-aa03-7cfdcbc1fb61"), Category.SODA, 2000, 2),
                new OrderItem(UUID.fromString("5f026bef-00b0-4345-a829-897953728de4"), Category.SODA, 1000, 1),
                new OrderItem(UUID.fromString("aed19a90-2ca9-4301-9de1-ebabbaacff6f"), Category.MILK, 1500, 3)

        );
        List<OrderItem> orderItems3 = List.of(
                new OrderItem(UUID.fromString("1bbc72f4-9ab3-498e-aa03-7cfdcbc1fb61"), Category.SODA, 2000, 3),
                new OrderItem(UUID.fromString("3e91e46b-bb87-45ec-906c-bdb5eb3edab4"), Category.FRUIT, 2500, 2),
                new OrderItem(UUID.fromString("5f026bef-00b0-4345-a829-897953728de4"), Category.SODA, 1000, 5),
                new OrderItem(UUID.fromString("aed19a90-2ca9-4301-9de1-ebabbaacff6f"), Category.MILK, 1500, 3)
        );

        List<Order> orders = new ArrayList<>(List.of(
                new Order(UUID.randomUUID(), new Email("test1@test.com"), "aa시 bb구 cc동", "00001", orderItems1, OrderStatus.ACCEPTED, now, now),
                new Order(UUID.randomUUID(), new Email("test2@test.com"), "dd시 ee구 fff동", "00002", orderItems2, OrderStatus.SHIPPING, now, now),
                new Order(UUID.randomUUID(), new Email("test3@test.com"), "jj시 qq구 ww동", "00003", orderItems3, OrderStatus.CANCELLED, now, now)
        ));
        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getOrderId().toString().compareTo(o2.getOrderId().toString());
            }
        });
        return orders;
    }

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    @DisplayName("주문이 DB에 잘 넣어지는지 테스트")
    void orderInsertTest() {
        Order newOrder = orderCreate();

        orderRepository.insert(newOrder);
        List<Order> orderList = orderRepository.findAll();

        assertThat(orderList).hasSize(1);
        assertThat(newOrder).usingRecursiveComparison().isEqualTo(orderList.get(0));
    }

    @Test
    @Transactional
    @DisplayName("주문 ID가 동일한 값이 들어올 경우 예외 발생")
    void duplicateOrderInsertTest() {
        Order newOrder = orderCreate();

        orderRepository.insert(newOrder);

        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> orderRepository.insert(newOrder));
    }

    @Test
    @Transactional
    @DisplayName("주문 정보가 업데이트가 잘 되는지 테스트")
    void orderUpdateTest() {
        Order newOrder = orderCreate();
        orderRepository.insert(newOrder);

        newOrder.setOrderStatus(OrderStatus.PAYMENT_CONFIRMED);
        orderRepository.update(newOrder);
        Optional<Order> modifyOrder = orderRepository.findById(newOrder.getOrderId());

        assertThat(modifyOrder).isPresent();
        assertThat(newOrder).usingRecursiveComparison().isEqualTo(modifyOrder.get());
    }

    @Test
    @Transactional
    @DisplayName("존재하지 않는 주문의 업데이트를 시도시 예외가 발생하는지 테스트")
    void notExistOrderUpdateTest() {
        Order newOrder = orderCreate();

        assertThatExceptionOfType(OrderNotMatchedException.class)
                .isThrownBy(() -> orderRepository.update(newOrder));
    }

    @Test
    @Transactional
    @DisplayName("DB에 존재하는 모든 주문이 잘 조회되는지 테스트")
    void orderFindAllTest() {
        List<Order> want = ordersCreate();

        for (Order order : want) {
            orderRepository.insert(order);
        }
        List<Order> got = orderRepository.findAll();

        assertThat(got).usingRecursiveComparison().isEqualTo(want);
    }

    @Test
    @Transactional
    @DisplayName("주문 ID 기준으로 DB에 조회가 가능한지 테스트")
    void orderIdFindOrderTest() {
        Order want = orderCreate();
        orderRepository.insert(want);

        Optional<Order> got = orderRepository.findById(want.getOrderId());

        assertThat(got).isPresent().get().usingRecursiveComparison().isEqualTo(want);
    }

    @Test
    @Transactional
    @DisplayName("주문 ID로 조회했을때 일치하는 주문이 없을때 빈 값이 반환되는지 테스트")
    void notExistOrderIdFindOrderTest() {
        Optional<Order> notExistOrder = orderRepository.findById(UUID.randomUUID());

        assertThat(notExistOrder).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("이메일을 기준으로 주문이 조회가 가능한지 테스트")
    void emailFindOrderTest() {
        Order want = orderCreate();
        orderRepository.insert(want);

        Optional<Order> got = orderRepository.findByEmail(want.getEmail());

        assertThat(got).isPresent().get().usingRecursiveComparison().isEqualTo(want);
    }

    @Test
    @Transactional
    @DisplayName("이메일로 주문을 조회했을 때 일치하는 주문이 없다면 빈 값이 반환되는지 테스트")
    void notExistEmailFindOrderTest() {
        Email email = new Email("test@test.co.kr");

        Optional<Order> notExistOrder = orderRepository.findByEmail(email);

        assertThat(notExistOrder).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("주문 ID로 주문을 삭제할 수 있는지 테스트")
    void orderIdDeleteTest() {
        Order order = orderCreate();

        orderRepository.insert(order);
        orderRepository.deleteById(order.getOrderId());
        Optional<Order> notExistOrder = orderRepository.findById(order.getOrderId());

        assertThat(notExistOrder).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("주문 ID와 매칭되는 주문이 없을 경우 예외가 발생해야 합니다.")
    void notExistOrderIdDeleteTest() {
        UUID orderId = UUID.randomUUID();
        assertThatExceptionOfType(OrderNotMatchedException.class)
                .isThrownBy(() -> orderRepository.deleteById(orderId));
    }
}