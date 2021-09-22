package com.programmers.coffeeorder.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeOrderDeliveryRequest {
    Long orderId;
    String sender;
    String receiver;
    String destination;
    String message;
}
