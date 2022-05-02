package org.programmers.gccoffee.repository;

import org.programmers.gccoffee.model.Order;
import org.programmers.gccoffee.model.OrderItem;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.programmers.gccoffee.ConvertingUtils.uuidToBytes;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate template;

    public OrderJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final String INSERT_SQL = "INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at)" +
            " VALUES (:orderId, :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)";
    private static final String ORDER_ITEMS_INSERT_SQL = "INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at)" +
            " VALUES (:orderId, :productId, :category, :price, :quantity, :createdAt, :updatedAt)";

    @Transactional
    @Override
    public Order insert(Order order) {
        template.update(INSERT_SQL, toOrderParamMap(order));
        order.getOrderItems().forEach(item -> template.update(ORDER_ITEMS_INSERT_SQL,
                toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
        return order;
    }

    private SqlParameterSource toOrderParamMap(Order order) {
        var paramMap = new MapSqlParameterSource();
        paramMap.addValue("orderId", uuidToBytes(order.getOrderId()));
        paramMap.addValue("email", order.getEmail().getAddress());
        paramMap.addValue("address", order.getAddress());
        paramMap.addValue("postcode", order.getPostcode());
        paramMap.addValue("orderStatus", order.getOrderStatus().toString());
        paramMap.addValue("createdAt", order.getCreatedAt());
        paramMap.addValue("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private SqlParameterSource toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {
        var paramMap = new MapSqlParameterSource();
        paramMap.addValue("orderId", uuidToBytes(orderId));
        paramMap.addValue("productId", uuidToBytes(item.productId()));
        paramMap.addValue("category", item.category().toString());
        paramMap.addValue("price", item.price());
        paramMap.addValue("quantity", item.quantity());
        paramMap.addValue("createdAt", createdAt);
        paramMap.addValue("updatedAt", updatedAt);
        return paramMap;
    }
}
