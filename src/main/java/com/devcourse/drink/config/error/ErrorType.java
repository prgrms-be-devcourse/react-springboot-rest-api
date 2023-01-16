package com.devcourse.drink.config.error;

import java.util.function.Supplier;

public enum ErrorType {
    PRICE_NEGATIVE_VALUE(ErrorProperties::getPriceNegativeValue),
    NOT_VALID_EMAIL(ErrorProperties::getNotValidEmail),
    ORDER_ITEM_NEGATIVE_VALUE(ErrorProperties::getOrderItemNegativeValue),
    PRODUCT_DB_INSERT_FAIL(ErrorProperties::getProductDBInsertFail),
    PRODUCT_NOT_MATCHED(ErrorProperties::getProductNotMatched),
    ORDER_NOT_MATCHED(ErrorProperties::getOrderNotMatched);

    private final Supplier<String> error;

    ErrorType(Supplier<String> error) {
        this.error = error;
    }

    public String message() {
        return error.get();
    }
}
