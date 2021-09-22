package com.programmers.coffeeorder.entity.delivery;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@SuperBuilder
public abstract class Delivery {
    protected Long id;
    protected DeliveryStatus deliveryStatus;
    protected String sender;
    protected String receiver;
    protected String destination;
    protected String message;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;


    public void registerId(long id) {
        this.id = id;
    }

    protected Delivery(Long id, String sender, String receiver, String destination) {
        this(id, DeliveryStatus.NOT_DELIVERED, sender, receiver, destination, "", LocalDateTime.now(), LocalDateTime.now());
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    protected abstract static class DTO {
        protected final long id;
        protected final DeliveryStatus deliveryStatus;
        protected final String sender;
        protected final String receiver;
        protected final String destination;
        protected final String message;
        protected final LocalDateTime createdAt;
        protected final LocalDateTime updatedAt;


        protected DTO(Delivery delivery) {
            this.id = delivery.id;
            this.deliveryStatus = delivery.deliveryStatus;
            this.sender = delivery.sender;
            this.receiver = delivery.receiver;
            this.destination = delivery.destination;
            this.message = delivery.message;
            this.createdAt = delivery.createdAt;
            this.updatedAt = delivery.updatedAt;
        }
    }
}
