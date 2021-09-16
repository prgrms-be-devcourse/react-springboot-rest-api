package com.programmers.coffeeorder.entity;

import lombok.Getter;

@Getter
public class DeliveryOrder extends Order {

    protected String address;
    protected int postcode;

    protected DeliveryOrder(long id, String address, int postcode) {
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

    public static class DTO extends Order.DTO {
        protected String address;
        protected int postcode;

        public DTO(long id, String address, int postcode) {
            super(id);
            this.address = address;
            this.postcode = postcode;
        }
    }
}
