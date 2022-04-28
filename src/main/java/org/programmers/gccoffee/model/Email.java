package org.programmers.gccoffee.model;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class Email {

    @Getter
    private final String address;

    private static final int MINIMUM_SIZE = 4;
    private static final int MAXIMUM_SIZE = 50;
    private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])";

    public Email(String address) {
        Assert.notNull(address, "The class must not be null");
        Assert.isTrue(address.length() >= MINIMUM_SIZE && address.length() <= MAXIMUM_SIZE,
                "address length must be between" + MINIMUM_SIZE + " and " + MAXIMUM_SIZE);
        Assert.isTrue(checkAddress(address), "Invalid email address");
        this.address = address;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches(EMAIL_REGEX, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;

        Email email = (Email) o;

        return address != null ? address.equals(email.address) : email.address == null;
    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }
}
