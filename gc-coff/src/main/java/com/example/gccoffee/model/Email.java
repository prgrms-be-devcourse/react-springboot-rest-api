package com.example.gccoffee.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email { // value object , email format으로 와야하기 때문에 생성
    private final String address;

    public Email(String address) {
        Assert.notNull(address, "address should not be null"); // 검증 null이 됐을때 예외가 되는 메세지
        Assert.isTrue(address.length() >= 4 && address.length() <= 50, "address length must be between 4 and 50 characters"); //표현식
        Assert.isTrue(checkAddress(address), "Invalid email address");
        this.address = address;
    }

    private static boolean checkAddress(String address) { // 즉 상속해도 오버라이드 못하게 하려고 쓰는 경우때문에 static을 붙인다. 즉 final method임을 의미한다.
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address); // 이 부분은 private static final로 빼는게 더 효율적이다. 자원 너무먹음
    }

    @Override
    public int hashCode( ) {
        return Objects.hash(address);
    }

    @Override
    public boolean equals(Object obj) { // equals와 hashCode는 오버라이드할떄 주읳야하던것같은
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Email email = (Email) obj;
        return Objects.equals(address, email.address);
    }

    @Override
    public String toString( ) { // 직접 구현하느게 좋
        return "Email{" + "address='" + address + '\'' + '}';
    }
}
