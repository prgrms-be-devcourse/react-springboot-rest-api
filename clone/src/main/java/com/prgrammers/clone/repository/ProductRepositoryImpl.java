package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.utils.TranslatorUtils;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", ProductRowMapper);
	}

	private static final RowMapper<Product> ProductRowMapper = (resultSet, index) -> {
		UUID productId = TranslatorUtils.toUUID(resultSet.getBytes("product_id"));
		String productName = resultSet.getString("product_name");
		Category category = Category.valueOf(resultSet.getString("category"));
		long price = resultSet.getLong("price");
		String description = resultSet.getString("description");
		LocalDateTime createdAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("updated_at"));
		return new Product(productId, productName, category, price, description, createdAt, updatedAt);
	};

	@Override
	public Product insert(Product product) {
		int update = jdbcTemplate.update(
				"INSERT INTO products(product_id, product_name, category, price,created_at, updated_at)"
						+ " values(UUID_TO_BIN(:productId),:productName,:category,:price,:createdAt,:updatedAt)",
				toParameters(product));

		if (update != 1) {
			throw new RuntimeException("not exe query ...");
		}

		return product;
	}

	@Override
	public Product update(Product product) {
		int update = jdbcTemplate.update(
				"update products set product_name = :productName, category = :category, price =:price,description = :description,updated_at =:updatedAt"
						+ " where product_id = (UUID_TO_BIN(:productId))",
				toParameters(product));

		if (update != 1) {
			throw new RuntimeException("not exe query ...");
		}

		return product;
	}

	@Override
	public Optional<Product> findById(UUID productId) {
		try {
			Product product = jdbcTemplate.queryForObject(
					"select * from products where product_id = UUID_TO_BIN(:productId)",
					Collections.singletonMap("productId", productId.toString().getBytes()), ProductRowMapper);

			return Optional.ofNullable(product);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Product> findByName(String productName) {
		return jdbcTemplate.query(
				"select * from products where product_name = :productName",
				Collections.singletonMap("productName", productName), ProductRowMapper);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return jdbcTemplate.query(
				"select * from products where category = :category",
				Collections.singletonMap("category", category.toString()), ProductRowMapper);

	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("DELETE FROM products", Collections.emptyMap());
	}

	private Map<String, Object> toParameters(Product product) {
		return new HashMap<>() {{
			put("productId", product.getProductId().toString().getBytes());
			put("productName", product.getProductName());
			put("category", product.getCategory().toString());
			put("price", product.getPrice());
			put("description", product.getDescription());
			put("createdAt", product.getCreatedAt());
			put("updatedAt", product.getUpdatedAt());
		}};
	}

}
