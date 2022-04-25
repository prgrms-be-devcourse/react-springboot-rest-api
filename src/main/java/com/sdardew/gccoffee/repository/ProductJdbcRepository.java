package com.sdardew.gccoffee.repository;

import com.sdardew.gccoffee.model.Category;
import com.sdardew.gccoffee.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sdardew.gccoffee.JdbcUtils.*;

public class ProductJdbcRepository implements ProductRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Product> findAll() {
    return jdbcTemplate.query("select * from products", productRowMapper);
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
  public Optional<Product> findByName(String productId) {
    return Optional.empty();
  }

  @Override
  public List<Product> findByCategory(Category category) {
    return null;
  }

  @Override
  public void deleteAll() {

  }

  private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
    var productId = toUUID(resultSet.getBytes("product_id"));
    var productName = resultSet.getString("product_name");
    var category = Category.valueOf(resultSet.getString("category"));
    var price = resultSet.getLong("price");
    var description = resultSet.getString("description");
    var cratedAt = toLocalDateTime(resultSet.getTimestamp("crated_at"));
    var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

    return new Product(productId, productName, category, price, description, cratedAt, updatedAt);
  };
}
