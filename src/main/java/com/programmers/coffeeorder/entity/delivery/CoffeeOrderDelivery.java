package com.programmers.coffeeorder.entity.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CoffeeOrderDelivery extends OrderDelivery {

    public void setCoffeeOrder(CoffeeOrder order) {
        this.order = order;
    }

    public CoffeeOrder getCoffeeOrder() {
        return (CoffeeOrder) order;
    }

    public DTO toDTO() {
        return new DTO(this);
    }

    public void registerSenderWithEmail() {
        this.sender = getCoffeeOrder().getEmail();
    }

    public void registerDestinationWithOrderInfo() {
        this.destination = String.format("%s(%d)", order.getAddress(), order.getPostcode());
    }

    @Getter
    @Setter
    public static class DTO extends OrderDelivery.DTO {
        public DTO(CoffeeOrderDelivery coffeeOrderDelivery) {
            super(coffeeOrderDelivery, coffeeOrderDelivery.getCoffeeOrder().toDTO());
        }

        @JsonIgnore
        public CoffeeOrder.DTO getCoffeeOrder() {
            return (CoffeeOrder.DTO) order;
        }
    }

}
