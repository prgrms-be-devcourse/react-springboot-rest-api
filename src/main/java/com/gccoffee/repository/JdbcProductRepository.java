package com.gccoffee.repository;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.gccoffee.Utils.toLocaleDateTime;
import static com.gccoffee.Utils.toUUID;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update(
            "INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) " +
                "VALUES(UNHEX(REPLACE(:productId, '-', '')), :productName, :category, :price, :description, " +
                ":createdAt, :updatedAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM products " +
                        "WHERE product_id = UNHEX(REPLACE(:productId, '-', ''))",
                    Collections.singletonMap("productId", productId.toString()), productRowMapper)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM products " +
                        "WHERE product_name = :productName",
                    Collections.singletonMap("productName", productName), productRowMapper)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query("SELECT * FROM products WHERE category = :category",
            Collections.singletonMap("category", category.toString()), productRowMapper);
    }

    @Override
    public void deleteAll() {

    }

    private static final RowMapper<Product> productRowMapper = (rs, i) -> {
        UUID productId = toUUID(rs.getBytes("product_id"));
        String productName = rs.getString("product_name");
        Category category = Category.valueOf(rs.getString("category"));
        Long price = rs.getLong("price");
        String description = rs.getString("description");
        LocalDateTime createdAt = toLocaleDateTime(rs.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocaleDateTime(rs.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    };

    private static final Map<String, Object> toParamMap(Product product) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", product.getProductId().toString().getBytes());
        map.put("productName", product.getProductName());
        map.put("category", product.getCategory().toString());
        map.put("price", product.getPrice());
        map.put("description", product.getDescription());
        map.put("createdAt", product.getCreatedAt());
        map.put("updatedAt", product.getUpdatedAt());
        return map;
    }
}
