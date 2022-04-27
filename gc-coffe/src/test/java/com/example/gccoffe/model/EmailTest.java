package com.example.gccoffe.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    
    @Test
    @DisplayName("잘못된 형식의 이메일 주소 - 예외 발생")
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email(("acccc"));
        });
    }

    @Test
    @DisplayName("유효한 형식의 이메일 주소 - Email이 잘 생성됨")
    public void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertTrue(email.getAddress().equals("hello@gmail.com"));
    }

    @Test
    @DisplayName("같은 주소를 가진 Email Object - eqauls() true")
    public void testEqEmail() {
        Email email = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email, email2);
    }

}