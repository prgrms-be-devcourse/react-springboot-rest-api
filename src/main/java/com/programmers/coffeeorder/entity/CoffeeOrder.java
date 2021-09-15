package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CoffeeOrder extends Order {
    private String email;
    private String name;
    private CoffeeType category;
    private int quantity;
    private int price;

    public CoffeeOrder(long id, String email, String name, CoffeeType category, int quantity, int price) {
        super(id);
        this.email = email;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateType(CoffeeType category) {
        this.category = category;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updatePrice(int price) {
        this.price = price;
    }
}
