package com.devcourse.drink.config.exception;

import com.devcourse.drink.config.error.ErrorType;

public class OrderNotMatchedException extends RuntimeException {

    public OrderNotMatchedException(ErrorType error) {
        super(error.message());
    }

}
