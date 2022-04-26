package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup(){
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository productRepository;

    private final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert(){
        productRepository.insert(newProduct);
        List<Product> all = productRepository.findAll();
        assertThat(all.isEmpty(),is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회 할 수 있다")
    void testFindByName(){
        Optional<Product> maybeProduct = productRepository.findByName(newProduct.getProductName());
        assertThat(maybeProduct.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디로 조회 할 수 있다")
    void testFindById(){
        Optional<Product> maybeProduct = productRepository.findById(newProduct.getProductId());
        assertThat(maybeProduct.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회 할 수 있다")
    void testFindByCategory(){
        List<Product> maybeProduct = productRepository.findByCategory(newProduct.getCategory());
        assertThat(maybeProduct.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정 할 수 있다.")
    void testUpdate(){

        newProduct.setProductName("updated-product");
        productRepository.update(newProduct);

        Optional<Product> maybeProduct = productRepository.findById(newProduct.getProductId());
        assertThat(maybeProduct.isEmpty(), is(false));
        assertThat(maybeProduct.get(),samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("상품을 전체 삭제한다.")
    void testDeleteAll(){
       productRepository.deleteAll();
       List<Product> all = productRepository.findAll();
       assertThat(all.isEmpty(),is(true));
    }
}