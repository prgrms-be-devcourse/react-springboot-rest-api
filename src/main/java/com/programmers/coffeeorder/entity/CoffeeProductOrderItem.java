package com.programmers.coffeeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoffeeProductOrderItem {
    private int quantity;
    private CoffeeProduct product;

    public DTO toDTO() {
        return new DTO(quantity, product);
    }

    @Getter
    @Setter
    public static class DTO {
        private int quantity;
        private CoffeeProduct.DTO product;

        public DTO(int quantity, CoffeeProduct product) {
            this.quantity = quantity;
            this.product = product.toDTO();
        }
    }
}
