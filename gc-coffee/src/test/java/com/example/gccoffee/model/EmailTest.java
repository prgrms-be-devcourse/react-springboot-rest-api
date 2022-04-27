package com.example.gccoffee.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("aaaa");
        });
    }

    @Test
    void testValidEmail() {
        var email = new Email("test@gmail.com");
        assertEquals("test@gmail.com", email.getAddress());
    }

    @Test
    void testEqEmail() {
        var email = new Email("test@gmail.com");
        var email2 = new Email("test@gmail.com");
        assertEquals(email, email2);
    }
}