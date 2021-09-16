package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CoffeeOrder extends Order {
    private String email;
    private String name;
    private CoffeeType category;
    private int quantity;
    private int price;
    private final LocalDateTime createdAt;

    public CoffeeOrder(Long id, String email, String coffeeName, CoffeeType coffeeCategory, int quantity, int price, LocalDateTime createdAt) {
        super(id);
        this.email = email;
        this.name = coffeeName;
        this.category = coffeeCategory;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
    }

    public void registerId(long id) { super.id = id; }

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

    public DTO toDTO() {
        return new DTO(id, email, name, category, quantity, price, createdAt);
    }

    @Getter
    @Setter
    public static class DTO extends Order.DTO {
        long id;
        private String email;
        private String coffeeName;
        private CoffeeType coffeeCategory;
        private int quantity;
        private int price;
        private LocalDateTime createdAt;

        public DTO(long id, String email, String coffeeName, CoffeeType coffeeCategory, int quantity, int price, LocalDateTime createdAt) {
            super(id);
            this.email = email;
            this.coffeeName = coffeeName;
            this.coffeeCategory = coffeeCategory;
            this.quantity = quantity;
            this.price = price;
            this.createdAt = createdAt;
        }
    }
}
