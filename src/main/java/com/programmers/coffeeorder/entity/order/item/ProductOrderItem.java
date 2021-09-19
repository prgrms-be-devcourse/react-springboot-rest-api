package com.programmers.coffeeorder.entity.order.item;

import com.programmers.coffeeorder.entity.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ProductOrderItem {
    protected int quantity;
    protected Product product;

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public abstract static class DTO {
        protected final int quantity;
        protected final Product.DTO product;
    }
}
