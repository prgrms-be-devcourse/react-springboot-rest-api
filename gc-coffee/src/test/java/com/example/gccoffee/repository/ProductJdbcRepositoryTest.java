package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.SqlScriptSource;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup(){
//        MysqldConfig config = aMysqldConfig(Version.v8_0_17)
//                .withCharset(Charset.UTF8)
//                .withPort(2215)
//                .withUser("", "test1234")
//                .withTimeZone("Asia/Seoul")
//                .build();
//        embeddedMysql = anEmbeddedMysql(config)
//                .addSchema("test-order-mgmt", ScriptResolver.classPathScripts("schema.sql"))
//                .start();
    }

    @AfterAll
    static void cleanup(){
        //embeddedMysql.stop();
    }



    @Autowired
    ProductRepository repository;

    private final Product newProduct = new Product(UUID.randomUUID(),"new-product", Category.COFFEE_BEAN_PACKAGE,1000L);

    @Test
    @Order(1)
    public void 상품을_추가할_수_있다(){
        repository.insert(newProduct);
        List<Product> all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    public void 상품을_이름으로_조회할수_있다(){
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(),is(false));
    }

    @Test
    @Order(3)
    public void 상품을_아이디로_조회할수_있다(){
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(),is(false));
    }

    @Test
    @Order(4)
    public void 상품을_카테고리로_조회할수_있다(){
        List<Product> products = repository.findByCategory(newProduct.getCategory());
        assertThat(products.isEmpty(),is(false));
    }

    @Test
    @Order(5)
    public void 상품을_수정_할_수_있다(){
        newProduct.setProductName("updated-product");
        repository.update(newProduct);

        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        System.out.println("product.get().getProductName() = " + product.get().getProductName());
        assertThat(product.get(),samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    public void 상품을_전체를_삭제한다(){
        repository.deleteAll();
        List<Product> all = repository.findAll();
        assertThat(all.isEmpty(),is(true));
    }


}