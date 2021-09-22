package com.programmers.coffeeorder.repository.delivery;

import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.repository.order.CoffeeOrderRepository;
import com.programmers.coffeeorder.repository.query.CoffeeDeliveryQuery;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.programmers.coffeeorder.repository.mapper.CoffeeOrderRowMappers.coffeeOrderDeliveryRowMapper;
import static com.programmers.coffeeorder.repository.mapper.CoffeeOrderRowMappers.coffeeOrderRowMapper;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeOrderDeliveryRepository implements CoffeeOrderDeliveryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;
    private final CoffeeDeliveryQuery coffeeDeliveryQuery;
    private final CoffeeOrderRepository coffeeOrderRepository;

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

    @Override
    public Optional<CoffeeOrderDelivery> readCoffeeOrderDelivery(long id) {
        List<CoffeeOrderDelivery> delivery = jdbcTemplate.query(coffeeDeliveryQuery.getSelect().getById(), coffeeOrderDeliveryRowMapper, id);
        if(delivery.isEmpty()) return Optional.empty();
        CoffeeOrderDelivery coffeeOrderDelivery = delivery.get(0);
        coffeeOrderRepository.readOrder(coffeeOrderDelivery.getOrderId()).ifPresent(coffeeOrderDelivery::setCoffeeOrder);
        return coffeeOrderDelivery.getCoffeeOrder() == null ? Optional.empty() : Optional.of(coffeeOrderDelivery);
    }

    @Override
    public CoffeeOrderDelivery createCoffeeOrderDelivery(CoffeeOrderDelivery coffeeOrderDelivery) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(coffeeDeliveryQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coffeeOrderDelivery.getDeliveryStatus().toString());
            statement.setString(2, coffeeOrderDelivery.getSender());
            statement.setString(3, coffeeOrderDelivery.getReceiver());
            statement.setString(4, coffeeOrderDelivery.getDestination());
            statement.setString(5, coffeeOrderDelivery.getMessage());
            statement.setLong(6, coffeeOrderDelivery.getCoffeeOrder().getId());
            return statement;
        }, keyHolder);

        coffeeOrderDelivery.registerId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return coffeeOrderDelivery;
    }
}
