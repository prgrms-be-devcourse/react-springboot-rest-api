package com.example.gccoffee.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testInvalidEmail(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email("acccc");
        });
    }

    @Test
    public void testValidEmail(){
        Email email = new Email("hello@gmail.com");
        assertTrue(email.getAddress().equals("hello@gmail.com"));
    }

    @Test
    public void testEqualityEmail(){
        Email email = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertTrue(email.equals(email2));
    }

}