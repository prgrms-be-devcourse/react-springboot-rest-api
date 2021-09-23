package com.programmers.coffeeorder.repository.mapper;

import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.delivery.DeliveryStatus;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.LinkedList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeOrderRowMappers {
    public static final RowMapper<CoffeeOrder> coffeeOrderRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("id");
        String address = rs.getString("address");
        int postcode = rs.getInt("postcode");
        String email = rs.getString("email");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.of(rs.getString("order_status"));
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .id(id)
                .email(email)
                .address(address)
                .postcode(postcode)
                .status(orderStatus)
                .orderItems(new LinkedList<>())
                .createdAt(createdAt)
                .updatedAt(updatedAt).build();

        long productId = rs.getLong("product_id");
        String productName = rs.getString("name");
        CoffeeType productCategory = CoffeeType.of(rs.getString("category"));
        int price = rs.getInt("price");
        String productDescription = rs.getString("description");
        CoffeeProduct coffeeProduct = new CoffeeProduct(productId, productName, productCategory, price, productDescription);

        int quantity = rs.getInt("quantity");
        coffeeOrder.getOrderItems().add(new CoffeeProductOrderItem(quantity, coffeeProduct));
        return coffeeOrder;
    };

    public static final RowMapper<CoffeeOrderDelivery> coffeeOrderDeliveryRowMapper = (rs, rowNum) -> CoffeeOrderDelivery.builder()
            .id(rs.getLong("id"))
            .deliveryStatus(DeliveryStatus.of(rs.getString("delivery_status")))
            .sender(rs.getString("sender"))
            .receiver(rs.getString("receiver"))
            .destination(rs.getString("destination"))
            .message(rs.getString("message"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
            .orderId(rs.getLong("order_Id")).build();
}