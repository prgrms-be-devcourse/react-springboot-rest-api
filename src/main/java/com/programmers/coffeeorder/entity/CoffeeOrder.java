package com.programmers.coffeeorder.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CoffeeOrder extends DeliveryOrder {
    private String email;
    private final List<CoffeeProduct> orderItems;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;

    public CoffeeOrder(
            Long id,
            String email,
            String address,
            int postcode,
            List<CoffeeProduct> orderItems) {
        this(
                id,
                email,
                address,
                postcode,
                OrderStatus.NOT_DELIVERED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                orderItems);
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
            List<CoffeeProduct> orderItems) {
        super(id, address, postcode);
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.orderItems = orderItems;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
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
        private long orderId;
        private String email;
        private List<CoffeeProduct.DTO> orderItems;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String status;

        public DTO(long id, String address, int postcode, String email, List<CoffeeProduct> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt, OrderStatus status) {
            super(id, address, postcode);
            this.email = email;
            this.orderItems = orderItems.stream().map(CoffeeProduct::toDTO).collect(Collectors.toList());
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.status = status.toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, postcode, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CoffeeOrder)) return false;
        CoffeeOrder other = (CoffeeOrder) obj;
        return id.equals(other.id);
    }
}
