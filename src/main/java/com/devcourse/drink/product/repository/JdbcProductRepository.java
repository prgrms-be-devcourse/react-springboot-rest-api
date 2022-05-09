package com.devcourse.drink.product.repository;

import com.devcourse.drink.config.exception.ProductNotMatchedException;
import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static com.devcourse.drink.config.error.ErrorType.PRODUCT_NOT_MATCHED;
import static com.devcourse.drink.util.JdbcUtils.toUUID;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static Map<String, Object> toParamMap(Product product) {
        return Map.of(
                "productId", product.getProductId().toString().getBytes(StandardCharsets.UTF_8),
                "name", product.getName(),
                "category", product.getCategory().toString(),
                "price", product.getPrice(),
                "description", product.getDescription(),
                "createdAt", product.getCreatedAt(),
                "updatedAt", product.getUpdatedAt()
        );
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String name = resultSet.getString("name");
        Category category = Category.valueOf(resultSet.getString("category"));
        Long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Product(
                productId,
                name,
                category,
                price,
                description,
                createdAt,
                updatedAt
        );
    };

    public JdbcProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        jdbcTemplate.update("INSERT INTO products(product_id, name, category, " +
                "price, description, created_at, updated_at) VALUES(UUID_TO_BIN(:productId), :name, :category, " +
                ":price, :description, :createdAt, :updatedAt)", toParamMap(product));
        return product;
    }

    @Override
    public Product update(Product product) {
        int update = jdbcTemplate.update("UPDATE products SET name = :name, category = :category, price = :price, " +
                "description = :description, created_at = :createdAt, updated_at = :updatedAt" +
                " WHERE product_id = UUID_TO_BIN(:productId)", toParamMap(product)
        );
        if (update != 1) {
            throw new ProductNotMatchedException(PRODUCT_NOT_MATCHED);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = UUID_TO_BIN(:productId)",
                            Collections.singletonMap("productId", productId.toString().getBytes(StandardCharsets.UTF_8)),
                            productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Product> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM products WHERE name = :name",
                    Collections.singletonMap("name", name),
                    productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "SELECT * FROM products WHERE category = :category",
                Collections.singletonMap("category", category.toString()),
                productRowMapper
        );
    }

    @Override
    public void deleteById(UUID productId) {
        int delete = jdbcTemplate.update("DELETE FROM products WHERE product_id = UUID_TO_BIN(:productId)", Map.of(
                "productId", productId.toString().getBytes(StandardCharsets.UTF_8)
        ));
        if (delete != 1) {
            throw new ProductNotMatchedException(PRODUCT_NOT_MATCHED);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM products", Collections.emptyMap());
    }
}
