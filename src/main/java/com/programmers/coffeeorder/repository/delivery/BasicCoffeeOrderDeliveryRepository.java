package com.programmers.coffeeorder.repository.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeType;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static com.programmers.coffeeorder.repository.mapper.CoffeeOrderRowMappers.*;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeOrderDeliveryRepository implements CoffeeOrderDeliveryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;

    @Override
    public Map<String, List<CoffeeOrder>> listReservedDeliveries(LocalDate date) {
        List<CoffeeOrder> coffeeOrders = jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper,
                LocalDateTime.of(date.minusDays(1), LocalTime.of(14, 0)),
                LocalDateTime.of(date, LocalTime.of(14, 0)));

        Map<String, List<CoffeeOrder>> groupByEmail = coffeeOrders.stream().collect(groupingBy(CoffeeOrder::getEmail, toList()));
        groupByEmail.forEach((email, orders) -> {
            Map<Long, List<CoffeeOrder>> groupByOrder = orders.stream().collect(groupingBy(CoffeeOrder::getId, toList()));
            List<CoffeeOrder> joinedOrder = groupByOrder.values().stream().map(
                    coffeeOrderList -> coffeeOrderList.stream().reduce((order1, order2) -> {
                        order1.getOrderItems().addAll(order2.getOrderItems());
                        return order1;
                    }).orElseThrow(() -> {
                        throw new IllegalArgumentException("Empty coffee order item record received.");
                    })).collect(toList());
            groupByEmail.put(email, joinedOrder);
        });

        return groupByEmail;
    }
}
