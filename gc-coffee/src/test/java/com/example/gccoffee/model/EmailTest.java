package com.example.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {
    @Test
    public void 이메일_테스트_실패() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("accc");
        });
    }

    @Test
    public void 이메일_테스트_성공() {
        final var email = new Email("hello@gamil.com");
        assertEquals("hello@gamil.com", email.getAddress());
    }

}