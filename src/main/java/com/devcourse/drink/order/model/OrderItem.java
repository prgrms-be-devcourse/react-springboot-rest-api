package com.devcourse.drink.order.model;

import com.devcourse.drink.product.model.Category;

import java.util.UUID;

import static com.devcourse.drink.config.error.ErrorType.*;

public record OrderItem(UUID uuid, Category category, long price, int count) {
    public OrderItem {
        if (count <= 0) {
            throw new IllegalArgumentException(PRICE_NEGATIVE_VALUE.message());
        }
    }
}
