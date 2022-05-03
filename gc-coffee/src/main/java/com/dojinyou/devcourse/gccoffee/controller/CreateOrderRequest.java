package com.dojinyou.devcourse.gccoffee.controller;

import com.dojinyou.devcourse.gccoffee.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode,
                                 List<OrderItem> orderItems) {
}
