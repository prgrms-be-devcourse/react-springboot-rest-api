package com.programmers.coffeeorder.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeOrderDeliveryUpdate {
    private long id;
    private String deliveryStatus;
    private String sender;
    private String receiver;
    private String destination;
    private String message;
    private long orderId;
}
