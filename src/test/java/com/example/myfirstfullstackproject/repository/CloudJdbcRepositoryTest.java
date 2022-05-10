package com.example.myfirstfullstackproject.repository;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CloudJdbcRepositoryTest {

    @TestConfiguration
    @ComponentScan(basePackages = "com.example.myfirstfullstackproject.repository")
    static class CloudJdbcRepositoryTestConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/contract_mgmt?useUnicode=true&serverTimezone=Asia/Seoul")
                    .username("test")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CloudRepository cloudRepository;

    private Cloud testItem;

    @Test
    @Order(1)
    @DisplayName("Hikari CP DataSource bean을 주입 받는다.")
    void testDataSourceInjection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("새로운 클라우드 상품을 저장한다.")
    void testInsert() {
        Cloud newCloud = Cloud.builder()
                .cloudName("TestLamda")
                .category(Category.COMPUTING)
                .price(10)
                .users(10)
                .storage(20)
                .img("lamda.png")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testItem = cloudRepository.insert(newCloud);

        assertThat(testItem.getCloudId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    @DisplayName("저장된 클라우드 상품을 카테고리별로 가져온다.")
    void testGetCloudsByCategory() {
        List<Cloud> cloudsByCategory = cloudRepository.findCloudsByCategory(Category.COMPUTING);

        assertThat(cloudsByCategory.get(0).getCloudId()).isEqualTo(testItem.getCloudId());
    }

    @Test
    @Order(4)
    @DisplayName("저장된 모든 상품을 가져온다.")
    void testFindAll() {
        List<Cloud> all = cloudRepository.findAll();

        assertThat(all)
                .filteredOn("category", Category.COMPUTING)
                .extracting("cloudId")
                .containsOnly(testItem.getCloudId());
    }

    @Test
    @Order(5)
    @DisplayName("아이디로 저장된 상품을 삭제한다.")
    void testDeleteById() {
        int deleteResult = cloudRepository.deleteById(testItem.getCloudId());

        assertThat(deleteResult).isEqualTo(1);
    }

}