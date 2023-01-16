package com.devcourse.drink.order.model;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.devcourse.drink.config.error.ErrorType.NOT_VALID_EMAIL;

public class Email {
    String address;

    public Email(String address) {
        emailValidateCheck(address);
        this.address = address;
    }

    private void emailValidateCheck(String address) {
        if (!Pattern.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", address)) {
            throw new IllegalArgumentException(NOT_VALID_EMAIL.message());
        }
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return address.equals(email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return address;
    }
}
