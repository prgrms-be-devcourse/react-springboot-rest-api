package com.gccoffee.repository;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gccoffee.Utils.toLocaleDateTime;
import static com.gccoffee.Utils.toUUID;

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
        return null;
    }
    
    @Override
    public Product update(Product product) {
        return null;
    }
    
    @Override
    public Optional<Product> findById(UUID productId) {
        return Optional.empty();
    }
    
    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.empty();
    }
    
    @Override
    public List<Product> findByCategory(String productName) {
        return null;
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
        LocalDateTime updatedAt = toLocaleDateTime(rs.getTimestamp("update_at"));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    };
}
