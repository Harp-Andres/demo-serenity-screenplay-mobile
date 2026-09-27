package com.harp.demo.screenplay.unit;

import com.harp.demo.screenplay.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void equalsBasedOnCredentials() {
        User a = new User("demo@test.com", "pass");
        User b = new User("demo@test.com", "pass");
        User c = new User("other@test.com", "pass");

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringDoesNotExposePassword() {
        assertEquals("User{username='u', password='***'}", new User("u", "secret").toString());
    }

    @Test
    void rejectsNullFields() {
        assertThrows(NullPointerException.class, () -> new User(null, "x"));
        assertThrows(NullPointerException.class, () -> new User("x", null));
    }
}
