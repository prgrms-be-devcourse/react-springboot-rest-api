package com.devcourse.drink.config.error;

import java.util.function.Supplier;

public enum ErrorType {
    PRICE_NEGATIVE_VALUE(ErrorProperties::getPriceNegativeValue),
    NOT_VALID_EMAIL(ErrorProperties::getNotValidEmail),
    ORDER_ITEM_NEGATIVE_VALUE(ErrorProperties::getOrderItemNegativeValue);

    private final Supplier<String> error;

    ErrorType(Supplier<String> error) {
        this.error = error;
    }

    public String message() {
        return error.get();
    }
}
