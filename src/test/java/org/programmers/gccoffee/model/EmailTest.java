package org.programmers.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email("illegalEmail"));
    }

    @Test
    void testValidEmail() {
        var email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    void equivalence() {
        var email1 = new Email("hello@gmail.com");
        var email2 = new Email("hello@gmail.com");
        assertEquals(email1, email2);
        assertEquals(email2, email1);
    }
}