package com.sdardew.gccoffee.repository;

import com.sdardew.gccoffee.model.Category;
import com.sdardew.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static com.wix.mysql.distribution.Version.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

  static EmbeddedMysql embeddedMysql;

  @BeforeAll
  static void setup() {
    var config = aMysqldConfig(v8_0_11)
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
  static void cleanup() {
    embeddedMysql.stop();
  }

  @Autowired
  ProductRepository repository;

  private final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

  @Test
  @Order(1)
  @DisplayName("상품을 추가할 수 있다")
  void testInsert() {
    repository.insert(newProduct);
    var all = repository.findAll();
    assertThat(all.isEmpty(), is(false));
  }
}