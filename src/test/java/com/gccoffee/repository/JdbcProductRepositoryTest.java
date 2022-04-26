package com.gccoffee.repository;

import com.gccoffee.model.Category;
import com.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
class JdbcProductRepositoryTest {
    
    static EmbeddedMysql embeddedMysql;
    
    @BeforeAll
    static void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .withTimeout(2, TimeUnit.MINUTES)
            .build();
        
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }
    
    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }
    
    @Autowired
    ProductRepository repository;
    
    private Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);
    
    @Test
    @Order(1)
    @DisplayName("시발")
    void testInsert() {
        repository.insert(newProduct);
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }
}