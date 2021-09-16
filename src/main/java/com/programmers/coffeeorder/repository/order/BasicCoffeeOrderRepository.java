package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.entity.CoffeeProduct;
import com.programmers.coffeeorder.entity.CoffeeType;
import com.programmers.coffeeorder.entity.OrderStatus;
import com.programmers.coffeeorder.repository.query.CoffeeOrderItemQuery;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeOrderRepository implements CoffeeOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;
    private final CoffeeOrderItemQuery coffeeOrderItemQuery;

    @Override
    public CoffeeOrder createOrder(CoffeeOrder coffeeOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(coffeeQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coffeeOrder.getAddress());
            statement.setInt(2, coffeeOrder.getPostcode());
            statement.setString(3, coffeeOrder.getEmail());
            statement.setString(4, coffeeOrder.getStatus().toString());
            return statement;
        }, keyHolder);

        coffeeOrder.registerId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        coffeeOrder.getOrderItems().forEach(coffeeProduct ->
                jdbcTemplate.update(
                        coffeeOrderItemQuery.getCreate(),
                        coffeeOrder.getId(),
                        coffeeProduct.getId()));

        return coffeeOrder;
    }

    @Override
    public Optional<CoffeeOrder> readOrder(long id) {
        coffeeOrderResultMap.clear();
        coffeeOrderProductResultMap.clear();

        try {
            jdbcTemplate.query(coffeeQuery.getSelect().getById(), coffeeOrderRowMapper, id);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }

        if(!coffeeOrderResultMap.containsKey(id)) {
            return Optional.empty();
        }

        CoffeeOrder coffeeOrder = coffeeOrderResultMap.get(id);
        List<CoffeeProduct> items = coffeeOrderProductResultMap.getOrDefault(id, new ArrayList<>(0));
        coffeeOrder.getOrderItems().addAll(items);
        return Optional.of(coffeeOrder);
    }

    @Override
    public List<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to) {
        coffeeOrderResultMap.clear();
        coffeeOrderProductResultMap.clear();

        jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper, from, to);

        return combineCoffeeOrderProduct();
    }

    @Override
    public List<CoffeeOrder> listDeliveryReservedOrders(LocalDate date) {
        coffeeOrderResultMap.clear();
        coffeeOrderProductResultMap.clear();

        jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper,
                LocalDateTime.of(date.minusDays(1), LocalTime.of(14, 0)),
                LocalDateTime.of(date, LocalTime.of(14, 0)));

        return combineCoffeeOrderProduct();
    }

    // long id로 나중에 조회할 때 Map<CoffeeOrder, List<CoffeeProduct>> 같은 경우는
    // key 를 long 으로 변환해서 비교해야 하기 때문에 그냥 두 개의 map을 뒀음. 성능, 메모리 상 장단점??
    // 동기화 문제???
    private static final Map<Long, CoffeeOrder> coffeeOrderResultMap = new HashMap<>();
    private static final Map<Long, List<CoffeeProduct>> coffeeOrderProductResultMap = new HashMap<>();
    private static final RowMapper<CoffeeOrder> coffeeOrderRowMapper = (rs, rowNum) -> {
        CoffeeOrder coffeeOrder;
        long id = rs.getLong("id");
        if(coffeeOrderResultMap.containsKey(id)) {
            coffeeOrder = coffeeOrderResultMap.get(id);
        } else {
            String address = rs.getString("address");
            int postcode = rs.getInt("postcode");
            String email = rs.getString("email");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
            OrderStatus orderStatus = OrderStatus.of(rs.getString("order_status"));
            coffeeOrder = new CoffeeOrder(id, email, address, postcode, orderStatus, createdAt, updatedAt);
            coffeeOrderResultMap.put(id, coffeeOrder);
        }

        long productId = rs.getLong("product_id");
        String productName = rs.getString("name");
        CoffeeType productCategory = CoffeeType.of(rs.getString("category"));
        int price = rs.getInt("price");
        String productDescription = rs.getString("description");
        CoffeeProduct coffeeProduct = new CoffeeProduct(productId, productName, productCategory, price, productDescription);

        List<CoffeeProduct> currentOrderProducts = coffeeOrderProductResultMap.getOrDefault(id, new LinkedList<>());
        currentOrderProducts.add(coffeeProduct);
        coffeeOrderProductResultMap.put(id, currentOrderProducts);

        return coffeeOrder;
    };

    private List<CoffeeOrder> combineCoffeeOrderProduct() {
        List<CoffeeOrder> result = new LinkedList<>();
        coffeeOrderResultMap.values().forEach(coffeeOrder -> {
            List<CoffeeProduct> items = coffeeOrderProductResultMap.getOrDefault(coffeeOrder.getId(), new ArrayList<>());
            coffeeOrder.getOrderItems().addAll(items);
            result.add(coffeeOrder);
        });

        return result;
    }
}
