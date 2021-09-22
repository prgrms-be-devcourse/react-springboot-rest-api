package com.programmers.coffeeorder.entity.delivery;

import com.programmers.coffeeorder.entity.order.DeliverableOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(of = {"order"}, callSuper = true)
@SuperBuilder
public abstract class OrderDelivery extends Delivery {
    protected DeliverableOrder order;

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
