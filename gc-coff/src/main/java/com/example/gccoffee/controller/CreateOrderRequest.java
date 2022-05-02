package com.example.gccoffee.controller;

import com.example.gccoffee.model.OrderItem;

import java.util.List;

public class CreateOrderRequest {
    private final String email;
    private final String address;
    private final String postcode;
    private final List<OrderItem> orderItems;


    public CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
    }

    public String getEmail( ) {
        return email;
    }

    public String getAddress( ) {
        return address;
    }

    public String getPostcode( ) {
        return postcode;
    }

    public List<OrderItem> getOrderItems( ) {
        return orderItems;
    }
}
