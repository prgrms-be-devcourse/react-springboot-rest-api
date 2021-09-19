package com.programmers.coffeeorder.entity.delivery;

import com.programmers.coffeeorder.entity.order.DeliveryOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"order", "deliveryStatus"}, callSuper = true)
public abstract class OrderDelivery extends Delivery {
    protected final DeliveryOrder order;
    protected DeliveryStatus deliveryStatus;

    protected OrderDelivery(Long id, DeliveryOrder order) {
        this(id, order, DeliveryStatus.NOT_DELIVERED);
    }

    protected OrderDelivery(Long id, DeliveryOrder order, DeliveryStatus deliveryStatus) {
        super(id);
        this.order = order;
        this.deliveryStatus = deliveryStatus;
    }

    protected OrderDelivery(Long id, DeliveryOrder order, DeliveryStatus deliveryStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.order = order;
        this.deliveryStatus = deliveryStatus;
    }

    public void setStatus(DeliveryStatus status) {
        this.deliveryStatus = status;
    }


    @Getter
    protected abstract static class DTO extends Delivery.DTO {
        protected final DeliveryOrder.DTO order;
        protected final DeliveryStatus status;

        protected DTO(Long id, DeliveryOrder.DTO order, DeliveryStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
            super(id, createdAt, updatedAt);
            this.order = order;
            this.status = status;
        }
    }
}
