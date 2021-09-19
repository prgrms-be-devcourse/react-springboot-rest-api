package com.programmers.coffeeorder.entity.order;

import com.programmers.coffeeorder.entity.product.coffee.CoffeeProductOrderItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(of = "email", callSuper = true)
public class CoffeeOrder extends DeliveryOrder {
    private String email;
    private final List<CoffeeProductOrderItem> orderItems;

    public CoffeeOrder(
            Long id,
            String email,
            String address,
            int postcode,
            List<CoffeeProductOrderItem> orderItems) {
        this(id, email, address, postcode, OrderStatus.CREATED, LocalDateTime.now(), LocalDateTime.now(), orderItems);
    }

    public CoffeeOrder(
            Long id,
            String email,
            String address,
            int postcode,
            OrderStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this(id, email, address, postcode, status, createdAt, updatedAt, new LinkedList<>());
    }

    public CoffeeOrder(
            Long id,
            String email,
            String address,
            int postcode,
            OrderStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<CoffeeProductOrderItem> orderItems) {
        super(id, address, postcode, status, createdAt, updatedAt);
        this.email = email;
        this.orderItems = orderItems;
    }

    public void registerId(long id) {
        super.id = id;
        updateTimestamp();
    }

    public void updateEmail(String email) {
        this.email = email;
        updateTimestamp();
    }

    public void changeDestination(String address) {
        this.address = address;
    }

    public void changePostcode(int postcode) {
        this.postcode = postcode;
    }

    public DTO toDTO() {
        return new DTO(id, address, postcode, email, orderItems, createdAt, updatedAt, status);
    }

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }

    @Getter
    @Setter
    public static class DTO extends DeliveryOrder.DTO {
        private String email;
        private List<CoffeeProductOrderItem.DTO> orderItems;

        public DTO(Long id, String address, int postcode, String email, List<CoffeeProductOrderItem> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt, OrderStatus status) {
            super(id, address, postcode, status, createdAt, updatedAt);
            this.email = email;
            this.orderItems = orderItems.stream().map(CoffeeProductOrderItem::toDTO).collect(Collectors.toList());
        }
    }
}
