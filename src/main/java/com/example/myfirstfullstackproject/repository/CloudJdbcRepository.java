package com.example.myfirstfullstackproject.repository;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CloudJdbcRepository implements CloudRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private static final RowMapper<Cloud> cloudRowMapper = (rs, rowNum) -> {
        long cloudId = rs.getLong("cloud_id");
        String cloudName = rs.getString("cloud_name");
        String category = rs.getString("category");
        long price = rs.getLong("price");
        int users = rs.getInt("users");
        int storage = rs.getInt("storage");
        String img = rs.getString("img");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

        return Cloud.builder()
                .cloudId(cloudId)
                .cloudName(cloudName)
                .category(Category.valueOf(category))
                .price(price)
                .users(users)
                .storage(storage)
                .img(img)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    };

    @Autowired
    public CloudJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("clouds")
                .usingGeneratedKeyColumns("cloud_id");
    }

    @Override
    public Cloud insert(Cloud cloud) {
        Map<String, Object> params = new HashMap<>();
        params.put("cloud_name", cloud.getCloudName());
        params.put("category", cloud.getCategory());
        params.put("price", cloud.getPrice());
        params.put("users", cloud.getUsers());
        params.put("storage", cloud.getStorage());
        params.put("img", cloud.getImg());
        params.put("created_at", Timestamp.valueOf(cloud.getCreatedAt()));
        params.put("updated_at", Timestamp.valueOf(cloud.getUpdatedAt()));

        long cloudId = Long.parseLong(String.valueOf(simpleJdbcInsert.executeAndReturnKey(params)));
        cloud.setCloudId(cloudId);

        return cloud;
    }

    @Override
    public List<Cloud> findCloudsByCategory(Category category) {
        String SELECT_BY_CATEGORY = "SELECT * FROM clouds WHERE category = ?";
        return jdbcTemplate.query(SELECT_BY_CATEGORY, cloudRowMapper, category.toString());
    }

    @Override
    public List<Cloud> findAll() {
        String SELECT_ALL = "SELECT * FROM clouds";
        return jdbcTemplate.query(SELECT_ALL, cloudRowMapper);
    }

    @Override
    public int deleteById(long cloudId) {
        String DELETE_BY_ID = "DELETE FROM clouds WHERE cloud_id = ?";
        return jdbcTemplate.update(DELETE_BY_ID, cloudId);
    }

}
