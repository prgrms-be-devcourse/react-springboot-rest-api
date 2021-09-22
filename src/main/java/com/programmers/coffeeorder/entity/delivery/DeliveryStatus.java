package com.programmers.coffeeorder.entity.delivery;

import java.util.function.Consumer;

public enum DeliveryStatus {
    CANCELLED(
            orderDelivery -> {
                throw new UnsupportedOperationException("CANCELLED ORDER CANNOT BE DELIVERED.");
            },
            orderDelivery -> {
                throw new UnsupportedOperationException("ALREADY CANCELLED ORDER CANNOT BE CANCELLED AGAIN.");
            }
    ),
    DELIVERED(
            orderDelivery -> {
                throw new UnsupportedOperationException("ALREADY DELIVERED ORDER CANNOT BE DELIVERED AGAIN.");
            },
            orderDelivery -> {
                throw new UnsupportedOperationException("DELIVERED ORDER CANNOT BE CANCELLED.");
            }
    ),
    NOT_DELIVERED(
            orderDelivery -> orderDelivery.setStatus(DELIVERED),
            orderDelivery -> orderDelivery.setStatus(CANCELLED)
    );

    // like strategy pattern?
    Consumer<OrderDelivery> deliver;
    Consumer<OrderDelivery> cancel;

    DeliveryStatus(Consumer<OrderDelivery> deliver, Consumer<OrderDelivery> cancel) {
        this.deliver = deliver;
        this.cancel = cancel;
    }

    public void deliver(OrderDelivery orderDelivery) {
        deliver.accept(orderDelivery);
    }

    public void cancel(OrderDelivery orderDelivery) {
        cancel.accept(orderDelivery);
    }

    public static DeliveryStatus of(String input) {
        try {
            return DeliveryStatus.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return DeliveryStatus.NOT_DELIVERED;
        }
    }
}
