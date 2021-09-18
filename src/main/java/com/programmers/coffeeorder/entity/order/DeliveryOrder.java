package com.programmers.coffeeorder.entity.order;

import lombok.Getter;

@Getter
public abstract class DeliveryOrder extends Order {

    protected String address;
    protected int postcode;

    protected DeliveryOrder(Long id, String address, int postcode) {
        super(id);
        this.address = address;
        this.postcode = postcode;
    }

    public void changeDestinationAddress(String address) {
        this.address = address;
    }

    public void changeDestinationPostcode(int postcode) {
        this.postcode = postcode;
    }

    @Getter
    public static class DTO extends Order.DTO {
        protected String address;
        protected int postcode;

        public DTO(Long id, String address, int postcode) {
            super(id);
            this.address = address;
            this.postcode = postcode;
        }
    }
}
