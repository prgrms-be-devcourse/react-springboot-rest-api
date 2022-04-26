package com.gccoffee.repository;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcProductRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .withTimeout(2, TimeUnit.MINUTES)
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("demo", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    private static final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        repository.insert(newProduct);
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindName() {
        var product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        var product = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product.isEmpty(), is(false));
    }
}