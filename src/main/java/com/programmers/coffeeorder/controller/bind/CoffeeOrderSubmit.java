package com.programmers.coffeeorder.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeOrderSubmit {
    private long orderId;
    private String coffeeName;
    private String category; // 처음부터 enum으로 받는 방법?
    private int price;
}
