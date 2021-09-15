package com.programmers.coffeeorder.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeOrderDetails {
    private long productId;
    private String category;
    private int price;
    private int quantity;
}
