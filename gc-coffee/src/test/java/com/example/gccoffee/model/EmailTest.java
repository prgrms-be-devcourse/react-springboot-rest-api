package com.example.gccoffee.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("올바르지 않은 이메일 양식 체크")
    public void testInvalidEmail() {

        assertThrows(IllegalArgumentException.class, ()
                -> {
            Email email = new Email("aaa");
        });
    }

    @Test
    @DisplayName("올바른 이메일 테스트")
    public void testValidEmail(){
        Email email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    @DisplayName("이메일이 동일한지 테스트")
    public void testEqualEmail(){
        Email email = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email, email2);
    }
}