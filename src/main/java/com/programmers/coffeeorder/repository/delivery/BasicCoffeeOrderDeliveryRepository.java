package com.programmers.coffeeorder.repository.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeType;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.util.stream.Collectors.*;

@Repository
@RequiredArgsConstructor
public class BasicCoffeeOrderDeliveryRepository implements CoffeeOrderDeliveryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;

    @Override
    public Map<String, List<CoffeeOrder>> listReservedDeliveries(LocalDate date) {
        coffeeOrderMap.clear();
        coffeeProductListMap.clear();

        jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderDeliveryRowMapper,
                LocalDateTime.of(date.minusDays(1), LocalTime.of(14, 0)),
                LocalDateTime.of(date, LocalTime.of(14, 0)));
        coffeeOrderMap.values().forEach(
                coffeeOrder -> {
                    List<CoffeeProduct> orderProducts = coffeeProductListMap.getOrDefault(coffeeOrder.getId(), new ArrayList<>(0));
                    Map<Long, List<CoffeeProduct>> collectedOrderProducts = orderProducts.stream().collect(groupingBy(CoffeeProduct::getId, toList()));
                    collectedOrderProducts.values().forEach(coffees ->
                            coffeeOrder.getOrderItems().add(new CoffeeProductOrderItem(coffees.size(), coffees.get(0))));
                });

        return coffeeOrderMap.values().stream().collect(groupingBy(CoffeeOrder::getEmail, toList()));
    }


    private final Map<Long, CoffeeOrder> coffeeOrderMap = new HashMap<>();
    private final Map<Long, List<CoffeeProduct>> coffeeProductListMap = new HashMap<>();
    private final RowMapper<CoffeeOrder> coffeeOrderDeliveryRowMapper = (rs, rowNum) -> {
        CoffeeOrder coffeeOrder;
        long id = rs.getLong("id");
        if (coffeeOrderMap.containsKey(id)) coffeeOrder = coffeeOrderMap.get(id);
        else {
            String email = rs.getString("email");
            String address = rs.getString("address");
            int postcode = rs.getInt("postcode");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
            OrderStatus orderStatus = OrderStatus.of(rs.getString("order_status"));
            coffeeOrder = new CoffeeOrder(id, email, address, postcode, orderStatus, createdAt, updatedAt);
            coffeeOrderMap.put(id, coffeeOrder);
        }

        long productId = rs.getLong("product_id");
        String productName = rs.getString("name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        CoffeeType coffeeType = CoffeeType.of(rs.getString("category"));
        CoffeeProduct coffeeProduct = new CoffeeProduct(productId, productName, coffeeType, price, description);
        List<CoffeeProduct> orderProducts = coffeeProductListMap.getOrDefault(id, new LinkedList<>());
        orderProducts.add(coffeeProduct);
        coffeeProductListMap.put(id, orderProducts);

        return coffeeOrder;
    };
}
