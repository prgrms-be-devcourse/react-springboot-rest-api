package org.programmers.gccoffee.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.gccoffee.model.Category;
import org.programmers.gccoffee.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {
    @Autowired
    private ProductJdbcRepository productRepository;

    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private final JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            template.update("DELETE FROM products");
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .addScript("h2/schema.sql")
                    .build();
        }

        @Bean
        public DataSourceCleaner dataSourceCleaner() {
            return new DataSourceCleaner(dataSource());
        }

        @Bean
        ProductJdbcRepository productRepository() {
            return new ProductJdbcRepository(dataSource());
        }
    }

    @BeforeEach
    void initializeTestData() {
        dataSourceCleaner.cleanDataBase();
        PRODUCT1.setDescription("상품1설명");
        PRODUCT2.setDescription("상품2설명");
        PRODUCT3.setDescription("상품3설명");
        productRepository.insert(PRODUCT1);
        productRepository.insert(PRODUCT2);
        productRepository.insert(PRODUCT3);
    }

    private static final Product PRODUCT1 = new Product(UUID.randomUUID(), "sampleProduct1", Category.COFFEE_BEAN_PACKAGE, 5000L);
    private static final Product PRODUCT2 = new Product(UUID.randomUUID(), "sampleProduct2", Category.COFFEE_BEAN_PACKAGE, 3500L);
    private static final Product PRODUCT3 = new Product(UUID.randomUUID(), "sampleProduct3", Category.COFFEE_BEAN_PACKAGE, 2500L);

    @Test
    @DisplayName("전체 상품 찾기")
    void findAll() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(PRODUCT1);
        expectedProducts.add(PRODUCT2);
        expectedProducts.add(PRODUCT3);

        List<Product> products = productRepository.findAll();

        assertEquals(expectedProducts.size(), products.size());
    }

    @Test
    @DisplayName("상품 갱신")
    void update() {
        var productWillUpdated = new Product(UUID.randomUUID(), "sampleProduct", Category.COFFEE_BEAN_PACKAGE, 1000L);
        productRepository.insert(productWillUpdated);
        setProductMutableFieldsByOthers(productWillUpdated, PRODUCT2);

        productRepository.update(productWillUpdated);
        var updatedProduct = productRepository.findById(productWillUpdated.getProductId()).get();

        assertTrue(assertMutableFieldsEquivalence(PRODUCT2, updatedProduct));
    }

    private Product setProductMutableFieldsByOthers(Product target, Product other) {
        target.setProductName(other.getProductName());
        target.setCategory(other.getCategory());
        target.setPrice(other.getPrice());
        target.setDescription(other.getDescription());

        return target;
    }

    private boolean assertMutableFieldsEquivalence(Product expected, Product actual) {
        //name, category, price, description 비교
        return (actual.getProductName().equals(expected.getProductName()) &&
                actual.getCategory().equals(expected.getCategory()) &&
                actual.getPrice() == (expected.getPrice()) &&
                actual.getDescription().equals(expected.getDescription()));
    }

    @Test
    @DisplayName("ID로 검색")
    void findById() {
        var foundProduct = productRepository.findById(PRODUCT1.getProductId());
        assertTrue(foundProduct.isPresent());
    }

    @Test
    @DisplayName("상품명으로 검색")
    void findByName() {
        var foundProduct = productRepository.findByName(PRODUCT3.getProductName());
        assertTrue(foundProduct.isPresent());
    }

    @Test
    @DisplayName("상품 종류롷 검색")
    void findByCategory() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(PRODUCT1);
        expectedProducts.add(PRODUCT2);
        expectedProducts.add(PRODUCT3);

        List<Product> foundProducts = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        assertEquals(expectedProducts.size(), foundProducts.size());
    }

    @Test
    @DisplayName("상품 전체 삭제")
    void deleteAll() {
        productRepository.deleteAll();
        List<Product> products = productRepository.findAll();
        assertEquals(0, products.size());
    }
}