package com.programmers.coffeeorder.controller.bind;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoffeeOrderSubmit {
    private String email;
    private String address;
    private int postcode;
    private List<CoffeeOrderDetails> orderItems;
}
