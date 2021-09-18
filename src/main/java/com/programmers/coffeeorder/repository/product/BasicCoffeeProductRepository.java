package com.programmers.coffeeorder.repository.product;

import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeType;
import com.programmers.coffeeorder.repository.query.CoffeeProductQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeProductRepository implements CoffeeProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CoffeeProductQuery coffeeProductQuery;

    @Override
    public List<CoffeeProduct> listCoffeeProducts() {
        return jdbcTemplate.query(coffeeProductQuery.getSelect().getAll(), coffeeProductRowMapper);
    }

    @Override
    public Optional<CoffeeProduct> findById(long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(coffeeProductQuery.getSelect().getById(), coffeeProductRowMapper, id));
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    private static final RowMapper<CoffeeProduct> coffeeProductRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        CoffeeType category = CoffeeType.of(rs.getString("category"));
        String description = rs.getString("description");
        int price = rs.getInt("price");
        return new CoffeeProduct(id, name, category, price, description);
    };

}
