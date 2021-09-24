package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.repository.mapper.CoffeeOrderRowMappers;
import com.programmers.coffeeorder.repository.product.CoffeeProductRepository;
import com.programmers.coffeeorder.repository.query.CoffeeOrderItemQuery;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BasicCoffeeOrderRepository implements CoffeeOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;
    private final CoffeeOrderItemQuery coffeeOrderItemQuery;

    private final CoffeeProductRepository coffeeProductRepository;

    @Override
    public CoffeeOrder createOrder(CoffeeOrder coffeeOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(coffeeQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coffeeOrder.getAddress());
            statement.setInt(2, coffeeOrder.getPostcode());
            statement.setString(3, coffeeOrder.getEmail());
            statement.setString(4, coffeeOrder.getStatus().toString());
            return statement;
        }, keyHolder);

        coffeeOrder.registerId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        coffeeOrder.getOrderItems().forEach(productOrderItem ->
        {
            CoffeeProduct product = ((CoffeeProductOrderItem) productOrderItem).getCoffeeProduct();
            coffeeProductRepository.findById(product.getId()).ifPresentOrElse(
                    menu -> {
                        product.update(menu);
                        jdbcTemplate.update(
                                coffeeOrderItemQuery.getCreate(),
                                coffeeOrder.getId(),
                                product.getId(),
                                productOrderItem.getQuantity());
                    },
                    () -> {
                        throw new IllegalArgumentException("Requested not existing coffee product from order " + coffeeOrder.getId());
                    });
        });

        return coffeeOrder;
    }

    @Override
    public Optional<CoffeeOrder> readOrder(long id) {
        List<CoffeeOrder> products = jdbcTemplate.query(coffeeQuery.getSelect().getById(), CoffeeOrderRowMappers.coffeeOrderRowMapper, id);
        return products.stream().reduce((order1, order2) -> {
            order1.getOrderItems().addAll(order2.getOrderItems());
            return order1;
        });
    }

    @Override
    public CoffeeOrder updateOrder(CoffeeOrder coffeeOrder) {
        jdbcTemplate.update(coffeeQuery.getUpdate().getById(),
                coffeeOrder.getAddress(),
                coffeeOrder.getPostcode(),
                coffeeOrder.getEmail(),
                LocalDateTime.now(),
                coffeeOrder.getStatus().toString(),
                coffeeOrder.getId());

        List<CoffeeOrder> notJoinedOrder = jdbcTemplate.query(coffeeQuery.getSelect().getById(), CoffeeOrderRowMappers.coffeeOrderRowMapper, coffeeOrder.getId());
        return notJoinedOrder.stream().reduce((order1, order2) -> {
            order1.getOrderItems().addAll(order2.getOrderItems());
            return order1;
        }).orElseThrow(() -> {
            throw new IllegalArgumentException("Empty coffee order record received.");
        });
    }

    @Override
    public void updateOrderItemsQuantity(long orderId, Map<Long, Integer> quantityMap) {
        quantityMap.forEach((productId, quantity) -> {
            Integer exist = jdbcTemplate.queryForObject(coffeeOrderItemQuery.getExists(), (rs, rowNum) -> rs.getInt("exist"), orderId, productId);
            if(exist == null || exist < 1) {
                jdbcTemplate.update(coffeeOrderItemQuery.getCreate(), orderId, productId, quantity);
            } else {
                jdbcTemplate.update(coffeeOrderItemQuery.getUpdate(), quantity, orderId, productId);
            }
        });
    }

    @Override
    public Collection<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to) {
        List<CoffeeOrder> coffeeOrders = jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), CoffeeOrderRowMappers.coffeeOrderRowMapper, from, to);

        Map<Long, List<CoffeeOrder>> coffeeOrderItemsByOrder = coffeeOrders.stream().collect(Collectors.groupingBy(CoffeeOrder::getId, Collectors.toList()));
        return coffeeOrderItemsByOrder.values().stream().map(notJoinedOrders ->
                notJoinedOrders.stream().reduce((order1, order2) -> {
                    order1.getOrderItems().addAll(order2.getOrderItems());
                    return order1;
                }).orElseThrow(() -> {
                    throw new IllegalArgumentException("Empty coffee order item record received.");
                })
        ).collect(Collectors.toList());
    }

}
