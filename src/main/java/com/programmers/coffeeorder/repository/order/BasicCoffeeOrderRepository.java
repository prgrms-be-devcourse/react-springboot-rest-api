package com.programmers.coffeeorder.repository.order;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.product.Product;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import com.programmers.coffeeorder.entity.product.coffee.CoffeeType;
import com.programmers.coffeeorder.repository.product.CoffeeProductRepository;
import com.programmers.coffeeorder.repository.query.CoffeeOrderItemQuery;
import com.programmers.coffeeorder.repository.query.CoffeeQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BasicCoffeeOrderRepository implements CoffeeOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CoffeeQuery coffeeQuery;
    private final CoffeeOrderItemQuery coffeeOrderItemQuery;

    private final CoffeeProductRepository coffeeProductRepository;

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

        coffeeOrder.getOrderItems().forEach(productOrderItem ->
        {
            CoffeeProductOrderItem coffeeProductOrderItem = (CoffeeProductOrderItem) productOrderItem;
            CoffeeProduct product = coffeeProductOrderItem.getCoffeeProduct();
            coffeeProductRepository.findById(product.getId()).ifPresentOrElse(
                    menu -> {
                        product.update(menu);
                        for(int i=0;i<coffeeProductOrderItem.getQuantity();i++) {
                            jdbcTemplate.update(
                                    coffeeOrderItemQuery.getCreate(),
                                    coffeeOrder.getId(),
                                    product.getId());
                        }
                    },
                    () -> log.warn("Requested not existing coffee product from order {}", coffeeOrder.getId()));
        });

        return coffeeOrder;
    }

    @Override
    public Optional<CoffeeOrder> readOrder(long id) {
        List<CoffeeOrder> products;
        try {
            products = jdbcTemplate.query(coffeeQuery.getSelect().getById(), coffeeOrderRowMapper, id);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }

        if(products.isEmpty()) return Optional.empty();

        Map<Product, List<CoffeeProductOrderItem>> groupByCoffeeType = products.stream()
                .map(coffeeOrder -> (CoffeeProductOrderItem) (coffeeOrder.getOrderItems().get(0)))
                .collect(Collectors.groupingBy(CoffeeProductOrderItem::getProduct, Collectors.toList()));
        CoffeeOrder orderInfo = products.get(0);
        List<CoffeeProductOrderItem> resultItems = new LinkedList<>();
        groupByCoffeeType.forEach((key, value) -> resultItems.add(new CoffeeProductOrderItem(value.size(), value.get(0).getCoffeeProduct())));
        CoffeeOrder coffeeOrder = new CoffeeOrder(
                id,
                orderInfo.getEmail(),
                orderInfo.getAddress(),
                orderInfo.getPostcode(),
                orderInfo.getStatus(),
                orderInfo.getCreatedAt(),
                orderInfo.getUpdatedAt(),
                resultItems);

        return Optional.of(coffeeOrder);
    }

    @Override
    public Collection<CoffeeOrder> listOrdersBetween(LocalDateTime from, LocalDateTime to) {
        List<CoffeeOrder> coffeeOrders = jdbcTemplate.query(coffeeQuery.getSelect().getCreatedBetween(), coffeeOrderRowMapper, from, to);
        Map<Long, CoffeeOrder> arrangedOrders = new TreeMap<>((o1, o2) -> (int)(o1 - o2));
        coffeeOrders.stream().collect(Collectors.groupingBy(CoffeeOrder::getId, Collectors.toList()))
                .forEach((key1, currentOrderCoffees) -> {
                    long orderId = key1;
                    CoffeeOrder orderInfo = currentOrderCoffees.get(0);

                    Map<Product, List<CoffeeOrder>> groupByCoffeeType = currentOrderCoffees.stream()
                            .collect(Collectors.groupingBy(coffeeOrder ->
                                    coffeeOrder.getOrderItems().get(0).getProduct(), Collectors.toList()));

                    List<CoffeeProductOrderItem> resultItems = new LinkedList<>();
                    groupByCoffeeType.forEach((key, value) -> resultItems.add(new CoffeeProductOrderItem(value.size(), (CoffeeProduct) key)));
                    arrangedOrders.put(orderId, new CoffeeOrder(
                            orderId,
                            orderInfo.getEmail(),
                            orderInfo.getAddress(),
                            orderInfo.getPostcode(),
                            orderInfo.getStatus(),
                            orderInfo.getCreatedAt(),
                            orderInfo.getUpdatedAt(),
                            resultItems));
                });
        return arrangedOrders.values();
    }

    private static final RowMapper<CoffeeOrder> coffeeOrderRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("id");
        String address = rs.getString("address");
        int postcode = rs.getInt("postcode");
        String email = rs.getString("email");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.of(rs.getString("order_status"));
        CoffeeOrder coffeeOrder = new CoffeeOrder(id, email, address, postcode, orderStatus, createdAt, updatedAt);

        long productId = rs.getLong("product_id");
        String productName = rs.getString("name");
        CoffeeType productCategory = CoffeeType.of(rs.getString("category"));
        int price = rs.getInt("price");
        String productDescription = rs.getString("description");
        CoffeeProduct coffeeProduct = new CoffeeProduct(productId, productName, productCategory, price, productDescription);

        coffeeOrder.getOrderItems().add(new CoffeeProductOrderItem(1, coffeeProduct));
        return coffeeOrder;
    };

}
