package com.programmers.coffeeorder.entity.delivery;

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
            CoffeeOrder coffeeOrder) {
        super(id, coffeeOrder);
    }

    public CoffeeOrderDelivery(
            Long id,
            CoffeeOrder coffeeOrder,
            DeliveryStatus deliveryStatus) {
        super(id, coffeeOrder, deliveryStatus);
    }

    public CoffeeOrderDelivery(
            Long id,
            CoffeeOrder coffeeOrder,
            DeliveryStatus deliveryStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(id, coffeeOrder, deliveryStatus, createdAt, updatedAt);
    }

    public CoffeeOrder getCoffeeOrder() {
        return (CoffeeOrder) order;
    }

    public DTO toDTO() {
        return new DTO(id, getCoffeeOrder().toDTO(), deliveryStatus, createdAt, updatedAt);
    }

    @Getter
    @Setter
    public static class DTO extends OrderDelivery.DTO {
        public DTO(
                Long id,
                CoffeeOrder.DTO order,
                DeliveryStatus status,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
            super(id, order, status, createdAt, updatedAt);
        }

        public CoffeeOrder.DTO getCoffeeOrder() {
            return (CoffeeOrder.DTO) order;
        }
    }
}
