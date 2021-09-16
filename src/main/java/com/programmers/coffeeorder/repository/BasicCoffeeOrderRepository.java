package com.programmers.coffeeorder.repository;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.CoffeeType;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeOrderRepository implements CoffeeOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;

    @Override
    public CoffeeOrder createOrder(CoffeeOrder coffeeOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(coffeeQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coffeeOrder.getEmail());
            statement.setString(2, coffeeOrder.getName());
            statement.setString(3, coffeeOrder.getCategory().toString());
            statement.setInt(4, coffeeOrder.getQuantity());
            statement.setInt(5, coffeeOrder.getPrice());
            return statement;
        }, keyHolder);
        coffeeOrder.registerId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return coffeeOrder;
    }

    @Override
    public Optional<CoffeeOrder> readOrder(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(coffeeQuery.getSelect().getById(), coffeeOrderRowMapper, id));
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper, from, to);
    }

    @Override
    public List<CoffeeOrder> listDeliveryReservedOrders(LocalDate date) {
        return jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper,
                LocalDateTime.of(date.minusDays(1), LocalTime.of(14, 0)),
                LocalDateTime.of(date, LocalTime.of(14, 0)));
    }

    private static final RowMapper<CoffeeOrder> coffeeOrderRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("id");
        String email = rs.getString("email");
        String name = rs.getString("name");
        CoffeeType category = CoffeeType.of(rs.getString("category"));
        int quantity = rs.getInt("quantity");
        int price = rs.getInt("price");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return new CoffeeOrder(id, email, name, category, quantity, price, createdAt);
    };
}
