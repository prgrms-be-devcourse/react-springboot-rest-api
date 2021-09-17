package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Order {
    protected Long id;

    protected Order(Long id) {
        this.id = id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DTO {
        private Long id;
    }
}
