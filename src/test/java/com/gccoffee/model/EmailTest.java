package com.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("aaaaaaa");
        });
    }
    
    @Test
    void testValidEmail() {
        var email = new Email("kate@gmail.com");
        assertEquals("kate@gmail.com", email.getAddress());
    }
    
    @Test
    void testEqEmail() {
        var email1 = new Email("kate@gmail.com");
        var email2 = new Email("kate@gmail.com");
        assertEquals(email1, email2);
    }
}