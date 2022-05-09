package com.gccoffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {
    private final UUID orderId;
    private final Email email;
    private String address;
    private String postcode;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
    
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
