package com.dojinyou.devcourse.gccoffee.repository;

import com.dojinyou.devcourse.gccoffee.model.Category;
import com.dojinyou.devcourse.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;
    private Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia /Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order-mgmt", ScriptResolver.classPathScripts("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductJdbcRepository repository;

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
    void testFindByName() {
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
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        var product = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        repository.update(newProduct);

        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(true));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("상품 전체를 삭제한다.")
    void testDeleteAll() {
        repository.deleteAll();
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(true));
    }

}