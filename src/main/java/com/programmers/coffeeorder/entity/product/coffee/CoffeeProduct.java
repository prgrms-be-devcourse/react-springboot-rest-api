package com.programmers.coffeeorder.entity.product.coffee;

import com.programmers.coffeeorder.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = {"coffeeName", "coffeeType"}, callSuper = true)
public class CoffeeProduct extends Product {
    private String coffeeName;
    private CoffeeType coffeeType;
    private int price;
    private String description;

    public DTO toDTO() {
        return new DTO(id, coffeeName, coffeeType.toString(), price);
    }

    public CoffeeProduct(Long id) {
        super(id);
    }

    public CoffeeProduct(Long id, String coffeeName, CoffeeType coffeeType, int price, String description) {
        super(id);
        this.coffeeName = coffeeName;
        this.coffeeType = coffeeType;
        this.price = price;
        this.description = description;
    }

    public void update(CoffeeProduct product) {
        this.coffeeName = product.coffeeName;
        this.coffeeType = product.coffeeType;
        this.price = product.price;
        this.description = product.description;
    }


    @Getter
    @Setter
    public static class DTO extends Product.DTO {
        String productName;
        String category;
        int price;

        public DTO(long productId, String productName, String category, int price) {
            super(productId);
            this.productName = productName;
            this.category = category;
            this.price = price;
        }
    }
}
