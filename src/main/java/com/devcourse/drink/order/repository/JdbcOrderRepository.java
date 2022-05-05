package com.devcourse.drink.order.repository;

import com.devcourse.drink.config.error.ErrorType;
import com.devcourse.drink.config.exception.OrderNotMatchedException;
import com.devcourse.drink.order.model.Email;
import com.devcourse.drink.order.model.Order;
import com.devcourse.drink.order.model.OrderItem;
import com.devcourse.drink.order.model.OrderStatus;
import com.devcourse.drink.product.model.Category;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static com.devcourse.drink.config.error.ErrorType.ORDER_NOT_MATCHED;
import static com.devcourse.drink.util.JdbcUtils.toUUID;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, Object> toOrderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(
            UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.productId().toString().getBytes());
        paramMap.put("category", item.category().toString());
        paramMap.put("price", item.price());
        paramMap.put("count", item.count());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);
        return paramMap;
    }

    private final RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        UUID orderId = toUUID(resultSet.getBytes("order_id"));
        Email email = new Email(resultSet.getString("email"));
        String address = resultSet.getString("address");
        String postcode = resultSet.getString("postcode");
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Order(orderId,
                email,
                address,
                postcode,
                orderItemFindById(orderId),
                orderStatus,
                createdAt,
                updatedAt);
    };

    private final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        int count = resultSet.getInt("count");
        return new OrderItem(productId, category, price, count);
    };
    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private void orderInsert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
                toOrderParamMap(order));
    }

    private void orderItemsInsert(Order order) {
        order.getOrderItems()
                .forEach(item -> jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, category, price, count, created_at, updated_at) " +
                                "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :count, :createdAt, :updatedAt)",
                        toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        orderInsert(order);
        orderItemsInsert(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        int update = jdbcTemplate.update("UPDATE orders " +
                        "SET order_status = :orderStatus, postcode = :postcode, address = :address, updated_at = :updatedAt",
                toOrderParamMap(order));
        if (update != 1) {
            throw new OrderNotMatchedException(ORDER_NOT_MATCHED);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("select * from orders", orderRowMapper);
    }

    private List<OrderItem> orderItemFindById(UUID orderId) {
        List<OrderItem> orderitems = jdbcTemplate.query(
                "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
                Collections.singletonMap("orderId", orderId.toString().getBytes(StandardCharsets.UTF_8)),
                orderItemRowMapper
        );
        return orderitems;
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                            Collections.singletonMap("orderId", orderId.toString().getBytes(StandardCharsets.UTF_8)),
                            orderRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> findByEmail(Email email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM orders WHERE email = :email",
                            Collections.singletonMap("email", email.getAddress()),
                            orderRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID orderId) {
        int delete = jdbcTemplate.update("DELETE FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                Map.of("orderId", orderId.toString().getBytes(StandardCharsets.UTF_8)));
        if (delete != 1) {
            throw new OrderNotMatchedException(ORDER_NOT_MATCHED);
        }
    }
}
