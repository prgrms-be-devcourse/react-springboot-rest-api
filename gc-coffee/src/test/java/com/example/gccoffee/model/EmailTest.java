package com.example.gccoffee.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("Email validtion 실패")
    public void testInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("acccc");
        });
    }

    @Test
    @DisplayName("Email validtion 성공")
    public void testValidEmail(){
        var email = new Email("hello@gmail.com");
        assertTrue(email.getAddress().equals("hello@gmail.com"));
    }

    @Test
    @DisplayName("Email Equality 테스트")
    public void testEqEmail(){
        var email = new Email("hello@gmail.com");
        var email2 = new Email("hello@gmail.com");
        assertEquals(email, email2);
    }
}