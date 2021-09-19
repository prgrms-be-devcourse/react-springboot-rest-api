package com.programmers.coffeeorder.entity.order;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"address", "postcode"}, callSuper = true)
public abstract class DeliveryOrder extends Order {
    protected String address;
    protected int postcode;
    protected OrderStatus status;

    protected DeliveryOrder(Long id, String address, int postcode) {
        this(id, address, postcode, OrderStatus.CREATED);
    }

    protected DeliveryOrder(Long id, String address, int postcode, OrderStatus status) {
        super(id);
        this.address = address;
        this.postcode = postcode;
        this.status = status;
    }

    protected DeliveryOrder(Long id, String address, int postcode, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.address = address;
        this.postcode = postcode;
        this.status = status;
    }

    public void changeDestinationAddress(String address) {
        this.address = address;
    }

    public void changeDestinationPostcode(int postcode) {
        this.postcode = postcode;
    }


    @Getter
    public abstract static class DTO extends Order.DTO {
        protected final String address;
        protected final int postcode;
        protected final OrderStatus status;

        protected DTO(Long id, String address, int postcode, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
            super(id, createdAt, updatedAt);
            this.address = address;
            this.postcode = postcode;
            this.status = status;
        }
    }
}
