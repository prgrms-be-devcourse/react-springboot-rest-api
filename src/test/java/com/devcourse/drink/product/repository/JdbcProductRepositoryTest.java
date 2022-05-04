package com.devcourse.drink.product.repository;

import com.devcourse.drink.config.exception.ProductNotMatchedException;
import com.devcourse.drink.product.model.Category;
import com.devcourse.drink.product.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class JdbcProductRepositoryTest {

    private static final List<Product> products = new ArrayList<>();
    static EmbeddedMysql embeddedMysql;

    @Autowired
    ProductRepository productRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @BeforeAll
    static void beforeAll() {
        products.add(new Product(UUID.randomUUID(), "cola", Category.SODA, 2000L, "콜라", LocalDateTime.now(), LocalDateTime.now()));
        products.add(new Product(UUID.randomUUID(), "cider", Category.SODA, 2000L, "사이다", LocalDateTime.now(), LocalDateTime.now()));
        products.add(new Product(UUID.randomUUID(), "ion", Category.SPORTS, 1500L, "이온음료", LocalDateTime.now(), LocalDateTime.now()));
        products.add(new Product(UUID.randomUUID(), "orange juice", Category.FRUIT, 3000L, "오렌지 주스", LocalDateTime.now(), LocalDateTime.now()));
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductId().toString().compareTo(p2.getProductId().toString());
            }
        });
    }

    @Test
    @Order(1)
    @DisplayName("DB에 데이터를 넣고 정상적으로 들어가있는지 확인합니다.")
    void findAllTest() {
        for (Product product : products) {
            productRepository.insert(product);
        }
        List<Product> got = productRepository.findAll();
        assertThat(got).usingRecursiveComparison().isEqualTo(products);
    }

    @Test
    @Order(2)
    @DisplayName("DB에 중복된 productId로 값을 넣으려고 시도할 경우 예외발생")
    void productIdDuplicateInsertTest() {
        Product product = products.get(0);
        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> productRepository.insert(product));
    }

    @Test
    @Order(3)
    @DisplayName("DB에 중복된 name을 값으로 넣으려고 시도할 경우 예외 발생")
    void nameDuplicateInsertTest() {
        Product product = new Product(UUID.randomUUID(),
                "cola",
                Category.SODA,
                1200L,
                "duplicate cola",
                LocalDateTime.now(),
                LocalDateTime.now());
        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> productRepository.insert(product));
    }

    @Test
    @Order(4)
    @DisplayName("특정 ID와 일치하는 상품을 조회할 수 있는지 테스트")
    void productIdFindTest() {
        UUID productId = products.get(0).getProductId();

        Optional<Product> got = productRepository.findById(productId);

        assertThat(got).isPresent().get().usingRecursiveComparison().isEqualTo(products.get(0));
    }

    @Test
    @Order(5)
    @DisplayName("존재하지 않는 ID를 값으로 전달했을때 빈 값이 전달되는지 테스트")
    void unknownProductIdTest() {
        UUID productId = UUID.randomUUID();

        Optional<Product> got = productRepository.findById(productId);

        assertThat(got).isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("특정 이름의 상품을 조회할 수 있는지 테스트")
    void nameFindTest() {
        String name = products.get(0).getName();

        Optional<Product> got = productRepository.findByName(name);

        assertThat(got).isPresent().get().usingRecursiveComparison().isEqualTo(products.get(0));
    }

    @Test
    @Order(7)
    @DisplayName("DB에 저장되어 있지 않은 상품의 이름을 조회할 경우 빈 값이 전달되는지 테스트")
    void unknownNameTest() {
        String name = "grape juice";

        Optional<Product> got = productRepository.findByName(name);

        assertThat(got).isEmpty();
    }

    @Test
    @Order(8)
    @DisplayName("특정 카테고리로 상품을 조회할 수 있는지 테스트")
    void categoryFindTest() {
        List<Product> want = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategory() == Category.SODA) {
                want.add(product);
            }
        }
        List<Product> got = productRepository.findByCategory(Category.SODA);
        assertThat(got).usingRecursiveComparison().isEqualTo(want);
    }

    @Test
    @Order(9)
    @DisplayName("특정 카테고리에 상품이 존재하지 않을 경우 빈 List가 반환되는지 테스트")
    void noneCategoryFind() {
        List<Product> got = productRepository.findByCategory(Category.MILK);

        assertThat(got).isEmpty();
    }

    @Test
    @Order(10)
    @DisplayName("DB에 특정 상품을 선택해 내용을 변경할 수 있는지 테스트")
    void productUpdateTest() {
        Product product = products.get(0);
        long want = 1900;

        product.setPrice(want);
        product = productRepository.update(product);

        assertThat(product.getPrice()).isEqualTo(want);
    }

    @Test
    @Order(11)
    @DisplayName("DB에 존재하지 않는 상품을 업데이트 시도시 예외가 발생하는지 테스트")
    void duplicateProductUpdateTest() {
        Product product = new Product(UUID.randomUUID(),
                "fanta",
                Category.SODA,
                2000L,
                "grape flavor",
                LocalDateTime.now(),
                LocalDateTime.now());

        assertThatExceptionOfType(ProductNotMatchedException.class)
                .isThrownBy(() -> productRepository.update(product));
    }

    @Test
    @Order(12)
    @DisplayName("특정 상품을 전달받아 해당 값을 DB에서 삭제되는지 테스트")
    void productDeleteTest() {
        Product product = products.get(0);

        productRepository.deleteById(product.getProductId());
        Optional<Product> got = productRepository.findById(product.getProductId());

        assertThat(got).isEmpty();
    }

    @Test
    @Order(13)
    @DisplayName("존재하지 않은 상품 정보를 전달받아 삭제할경우 예외 발생하는지 테스트")
    void notExistProductDeleteTest() {
        UUID productId = products.get(0).getProductId();

        assertThatExceptionOfType(ProductNotMatchedException.class)
                .isThrownBy(() -> productRepository.deleteById(productId));
    }

    @Test
    @Order(14)
    @DisplayName("존재하는 전체 상품을 삭제")
    void productDeleteAllTest() {
        productRepository.deleteAll();

        List<Product> got = productRepository.findAll();

        assertThat(got).isEmpty();
    }
}