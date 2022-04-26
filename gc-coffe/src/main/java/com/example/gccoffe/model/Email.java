package com.example.gccoffe.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private final String address;

    public Email(String address) {
        // Email data validation
        // check null
        Assert.notNull(address, "Address should not be null.");
        // check length
        Assert.isTrue(address.length() >= 4 && address.length() <= 50,
                "Address length must be between 4 and 50 characters.");
        // regex를 이용해서 메시지 포맷이 이메일이 받는지 확인
        Assert.isTrue(checkAddress(address), "Invalid email address.");
        this.address = address;
    }

    /**
     * 들어온 값이 이메일 형식이 맞는지 확인
     * @param address
     * @return 패턴이 매치되면 true
     */
    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email{");
        sb.append("address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAddress() {
        return address;
    }
}
