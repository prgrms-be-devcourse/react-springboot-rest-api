package com.devcourse.drink.product.model;

public enum Category {
    SODA("탄산음료"),
    FRUIT("과일음료"),
    SPORTS("이온음료"),
    MILK("우유");

    final String value;

    Category(String value) {
        this.value = value;
    }
}
