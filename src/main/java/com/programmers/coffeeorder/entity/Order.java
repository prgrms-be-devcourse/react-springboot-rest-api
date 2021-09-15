package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Order {
    protected final long id;

    protected Order(long id) {
        this.id = id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DTO {
        private long id;
    }
}
