package com.programmers.coffeeorder.entity.order;

import com.programmers.coffeeorder.entity.order.item.ProductOrderItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode(of = {"address", "postcode"}, callSuper = true)
@SuperBuilder
public abstract class DeliverableOrder extends Order {
    protected String address;
    protected int postcode;
    protected OrderStatus status;
    protected List<ProductOrderItem> orderItems; // List<ProductOrderItem> not compatible with List<CoffeeProductOrderItem>

    protected DeliverableOrder(
            Long id,
            String address,
            int postcode,
            OrderStatus status,
            List<ProductOrderItem> orderItems) {
        super(id);
        this.address = address;
        this.postcode = postcode;
        this.status = status;
        this.orderItems = orderItems;
    }

    protected DeliverableOrder(
            Long id,
            String address,
            int postcode,
            OrderStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<ProductOrderItem> orderItems) {
        super(id, createdAt, updatedAt);
        this.address = address;
        this.postcode = postcode;
        this.status = status;
        this.orderItems = orderItems;
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
        protected final OrderStatus orderStatus;
        protected final List<ProductOrderItem.DTO> orderItems;

        protected DTO(Long id, String address, int postcode, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<ProductOrderItem.DTO> orderItems) {
            super(id, createdAt, updatedAt);
            this.address = address;
            this.postcode = postcode;
            this.orderStatus = orderStatus;
            this.orderItems = orderItems;
        }
    }
}
