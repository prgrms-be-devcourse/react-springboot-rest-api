package com.programmers.coffeeorder.entity.delivery;

import com.programmers.coffeeorder.entity.order.DeliverableOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"order"}, callSuper = true)
public abstract class OrderDelivery extends Delivery {
    protected final DeliverableOrder order;

    protected OrderDelivery(Long id,
                            String receiver,
                            String destination,
                            DeliverableOrder order) {
        super(id, receiver, destination);
        this.order = order;
    }

    protected OrderDelivery(Long id,
                            String receiver,
                            String destination,
                            DeliverableOrder order,
                            DeliveryStatus deliveryStatus) {
        this(id, receiver, destination, order, deliveryStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    protected OrderDelivery(Long id,
                            String receiver,
                            String destination,
                            DeliverableOrder order,
                            DeliveryStatus deliveryStatus,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        super(id, deliveryStatus, "Anonymous", receiver, destination, "", createdAt, updatedAt);
        this.order = order;
    }

    public void setStatus(DeliveryStatus status) {
        this.deliveryStatus = status;
    }


    @Getter
    protected abstract static class DTO extends Delivery.DTO {
        protected final DeliverableOrder.DTO order;

        protected DTO(OrderDelivery delivery, DeliverableOrder.DTO order) {
            super(delivery);
            this.order = order;
        }
    }
}
