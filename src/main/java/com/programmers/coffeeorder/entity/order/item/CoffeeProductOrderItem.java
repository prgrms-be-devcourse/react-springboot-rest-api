package com.programmers.coffeeorder.entity.order.item;

import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeProductOrderItem extends ProductOrderItem {

    public CoffeeProductOrderItem(int quantity, CoffeeProduct coffeeProduct) {
        super(quantity, coffeeProduct);
    }

    public CoffeeProduct getCoffeeProduct() {
        return (CoffeeProduct) product;
    }

    public DTO toDTO() {
        return new DTO(quantity, getCoffeeProduct());
    }

    @Getter
    @Setter
    public static class DTO extends ProductOrderItem.DTO{
        public DTO(int quantity, CoffeeProduct product) {
            super(quantity, product.toDTO());
        }

        public CoffeeProduct.DTO getCoffeeProduct() {
            return (CoffeeProduct.DTO) product;
        }
    }
}
