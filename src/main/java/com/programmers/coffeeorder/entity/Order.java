package com.programmers.coffeeorder.entity;

import lombok.Getter;

@Getter
public abstract class Order {
    private final long id;

    protected Order(long id) {
        this.id = id;
    }
}
