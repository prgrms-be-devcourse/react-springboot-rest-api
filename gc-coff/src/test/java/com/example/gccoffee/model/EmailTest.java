package com.example.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, ()->{
            var email = new Email("accccc");
        });
    }
    @Test
    public void testValidEmail() {
        var email = new Email("gnsrl76@naver.com");
        assertEquals("gnsrl76@naver.com", email.getAddress());
    }
    @Test
    public void testEqEmail() {
        var email = new Email("gnsrl76@naver.com");
        var email2 = new Email("gnsrl76@naver.com");
        assertEquals(email,email2);
    }
}