package com.programmers.coffeeorder.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.coffeeorder.controller.bind.CoffeeOrderUpdate;
import com.programmers.coffeeorder.entity.order.item.CoffeeProductOrderItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(of = "email", callSuper = true)
@SuperBuilder
public class CoffeeOrder extends DeliverableOrder {
    private String email;

    public CoffeeOrder(CoffeeOrderUpdate update) {
        super(update.getId(), update.getAddress(), update.getPostcode(), OrderStatus.of(update.getOrderStatus()), new ArrayList<>(0));
        this.email = update.getEmail();
    }

    public List<CoffeeProductOrderItem> getCoffeeOrderItems() {
        return orderItems.stream().map(CoffeeProductOrderItem.class::cast).collect(Collectors.toList());
    }

    public void registerId(long id) {
        super.id = id;
        updateTimestamp();
    }

    public void changeEmail(String email) {
        this.email = email;
        updateTimestamp();
    }

    public void changeDestination(String address) {
        this.address = address;
    }

    public void changePostcode(int postcode) {
        this.postcode = postcode;
    }

    public DTO toDTO() {
        return new DTO(this);
    }

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }

    @Getter
    @Setter
    public static class DTO extends DeliverableOrder.DTO {
        private String email;

        public DTO(CoffeeOrder coffeeOrder) {
            super(coffeeOrder.id, coffeeOrder.address, coffeeOrder.postcode, coffeeOrder.status,
                    coffeeOrder.createdAt, coffeeOrder.updatedAt,
                    coffeeOrder.orderItems.stream()
                            .map(CoffeeProductOrderItem.class::cast)
                            .map(CoffeeProductOrderItem::toDTO).collect(Collectors.toList()));
            this.email = coffeeOrder.getEmail();
        }

        @JsonIgnore
        public List<CoffeeProductOrderItem.DTO> getCoffeeOrderItems() { // thymeleaf file(deliveries-scheduled.html) use this.
            return orderItems.stream().map(CoffeeProductOrderItem.DTO.class::cast).collect(Collectors.toList());
        }

    }
}
