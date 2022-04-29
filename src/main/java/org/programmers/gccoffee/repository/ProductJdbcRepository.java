package org.programmers.gccoffee.repository;

import org.programmers.gccoffee.model.Category;
import org.programmers.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static java.util.Collections.singletonMap;
import static org.programmers.gccoffee.ConvertingUtils.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private static final String FIND_ALL_SQL = "SELECT * FROM products";
    private static final String INSERT_SQL = "INSERT INTO products (product_id, product_name, category, price, description, created_at, updated_at)" +
            " values(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)";
    private static final String UPDATE_SQL = "UPDATE products SET product_name = :productName, category = :category," +
            " price = :price, description = :description, updated_at = :updatedAt WHERE product_id = :productId";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE product_id = :productId";
    private static final String FIND_BY_NAME = "SELECT * FROM products WHERE product_name = :productName";
    private static final String FIND_BY_CATEGORY = "SELECT * FROM products WHERE category = :category";
    private static final String DELETE_ALL_SQL = "DELETE FROM products";

    private final NamedParameterJdbcTemplate template;

    public ProductJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        return template.query(FIND_ALL_SQL, productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        int updatedRow = template.update(INSERT_SQL, toParamMap(product));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        return product;
    }

    private SqlParameterSource toParamMap(Product product) {
        var paramMap = new MapSqlParameterSource();
        paramMap.addValue("productId", uuidToBytes(product.getProductId()));
        paramMap.addValue("productName", product.getProductName());
        paramMap.addValue("category", product.getCategory().toString());
        paramMap.addValue("price", product.getPrice());
        paramMap.addValue("description", product.getDescription());
        paramMap.addValue("createdAt", product.getCreatedAt());
        paramMap.addValue("updatedAt", product.getUpdatedAt());
        return paramMap;
    }

    @Override
    public Product update(Product product) {
        int updatedRow = template.update(UPDATE_SQL, toParamMap(product));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.of(template.queryForObject(FIND_BY_ID, singletonMap("productId",
                    uuidToBytes(productId)), productRowMapper));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.of(template.queryForObject(FIND_BY_NAME, singletonMap("productName",
                    productName), productRowMapper));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return template.query(FIND_BY_CATEGORY, singletonMap("category",
                category.toString()), productRowMapper);
    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private static final RowMapper<Product> productRowMapper = (rs, i) -> {
        var productId = bytesToUuid(rs.getBytes("product_id"));
        var productName = rs.getString("product_name");
        var category = Category.valueOf(rs.getString("category"));
        var price = rs.getLong("price");
        var description = rs.getString("description");
        var createdAt = timestampToLocalDateTime(rs.getTimestamp("created_at"));
        var updatedAt = timestampToLocalDateTime(rs.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    };
}
