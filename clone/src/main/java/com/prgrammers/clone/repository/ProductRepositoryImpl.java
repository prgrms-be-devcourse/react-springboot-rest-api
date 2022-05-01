package com.prgrammers.clone.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.prgrammers.clone.model.Category;
import com.prgrammers.clone.model.Product;
import com.prgrammers.clone.utils.TranslatorUtils;

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
	public Optional<Product> findByCategory(Category category) {
		return Optional.empty();
	}

	@Override
	public void deleteAll() {

	}

}
