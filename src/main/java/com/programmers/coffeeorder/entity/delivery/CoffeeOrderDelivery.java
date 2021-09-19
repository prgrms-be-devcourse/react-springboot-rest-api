package com.programmers.coffeeorder.entity.delivery;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.DeliveryOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CoffeeOrderDelivery extends OrderDelivery {
    public CoffeeOrderDelivery(
            Long id,
            CoffeeOrder order) {
        super(id, order);
    }

    public CoffeeOrderDelivery(
            Long id,
            CoffeeOrder order,
            DeliveryStatus deliveryStatus) {
        super(id, order, deliveryStatus);
    }

    public CoffeeOrderDelivery(
            Long id,
            CoffeeOrder order,
            DeliveryStatus deliveryStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(id, order, deliveryStatus, createdAt, updatedAt);
    }

    public CoffeeOrder getCoffeeOrder() {
        return (CoffeeOrder) order;
    }

    public DTO toDTO() {
        return new DTO(id, ((CoffeeOrder) order).toDTO(), deliveryStatus, createdAt, updatedAt);
    }

    @Getter
    @Setter
    public static class DTO extends OrderDelivery.DTO {
        public DTO(
                Long id,
                DeliveryOrder.DTO order,
                DeliveryStatus status,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
            super(id, order, status, createdAt, updatedAt);
        }
    }
}
