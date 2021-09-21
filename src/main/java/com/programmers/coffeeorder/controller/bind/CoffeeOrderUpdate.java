package com.programmers.coffeeorder.controller.bind;

import com.programmers.coffeeorder.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CoffeeOrderUpdate {
    private long id;
    private String email;
    private String address;
    private int postcode;
    private String orderStatus;
    private Map<Long, Integer> orderItems;
}
