package com.programmers.coffeeorder.entity.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CoffeeOrderDelivery extends OrderDelivery {

    public CoffeeOrderDelivery(
            Long id,
            String receiver,
            CoffeeOrder coffeeOrder) {
        this(
                id,
                receiver,
                String.format("%s(%s)", coffeeOrder.getAddress(), coffeeOrder.getPostcode()),
                coffeeOrder,
                DeliveryStatus.NOT_DELIVERED);
    }

    public CoffeeOrderDelivery(
            Long id,
            String receiver,
            String destination,
            CoffeeOrder coffeeOrder,
            DeliveryStatus deliveryStatus) {
        this(id, receiver, destination, coffeeOrder, deliveryStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    public CoffeeOrderDelivery(
            Long id,
            String receiver,
            String destination,
            CoffeeOrder coffeeOrder,
            DeliveryStatus deliveryStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(id, receiver, destination, coffeeOrder, deliveryStatus, createdAt, updatedAt);
    }

    public CoffeeOrder getCoffeeOrder() {
        return (CoffeeOrder) order;
    }

    public DTO toDTO() {
        return new DTO(this);
    }

    @Getter
    @Setter
    public static class DTO extends OrderDelivery.DTO {
        public DTO(CoffeeOrderDelivery coffeeOrderDelivery) {
            super(coffeeOrderDelivery, coffeeOrderDelivery.getCoffeeOrder().toDTO());
        }

        @JsonIgnore
        public CoffeeOrder.DTO getCoffeeOrder() {
            return (CoffeeOrder.DTO) order;
        }
    }
}
