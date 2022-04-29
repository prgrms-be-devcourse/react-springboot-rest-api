package com.example.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testEmail() {
        assertThrows(IllegalArgumentException.class, ()->{
            var email = new Email("accccc");
        });
    }
}