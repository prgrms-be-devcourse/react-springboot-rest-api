package com.prgrammers.clone.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.prgrammers.clone.repository.OrderRepository;
import com.prgrammers.clone.repository.OrderRepositoryImpl;
import com.prgrammers.clone.repository.ProductRepository;
import com.prgrammers.clone.repository.ProductRepositoryImpl;
import com.zaxxer.hikari.HikariDataSource;

@TestConfiguration
public class TestJdbcConfig {

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.url("jdbc:mysql://localhost:3306/coffee_bean?serverTimezone=Asia/Seoul")
				.username("root")
				.password("")
				.type(HikariDataSource.class)
				.build();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public ProductRepository productRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new ProductRepositoryImpl(namedParameterJdbcTemplate);
	}

	@Bean
	public OrderRepository orderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new OrderRepositoryImpl(namedParameterJdbcTemplate);
	}
}
