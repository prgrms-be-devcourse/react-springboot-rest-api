package com.devcourse.drink.config.exception;

import com.devcourse.drink.config.error.ErrorType;

public class ProductNotMatchedException extends RuntimeException {
    public ProductNotMatchedException(ErrorType error) {
        super(error.message());
    }
}
