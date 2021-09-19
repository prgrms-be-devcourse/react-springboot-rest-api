package com.programmers.coffeeorder.entity.product;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public abstract class Product {
    protected Long id;

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public abstract static class DTO {
        protected final long productId;
    }
}
