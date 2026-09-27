package com.harp.demo.screenplay.unit;

import com.harp.demo.screenplay.models.User;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pure domain unit tests reported through Serenity (no Appium / no device).
 */
@ExtendWith(SerenityJUnit5Extension.class)
class UserTest {

    @Test
    @Title("User equality is based on credentials")
    void equalsBasedOnCredentials() {
        User a = new User("demo@test.com", "pass");
        User b = new User("demo@test.com", "pass");
        User c = new User("other@test.com", "pass");

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @Title("User toString masks the password")
    void toStringDoesNotExposePassword() {
        assertEquals("User{username='u', password='***'}", new User("u", "secret").toString());
    }

    @Test
    @Title("User rejects null username or password")
    void rejectsNullFields() {
        assertThrows(NullPointerException.class, () -> new User(null, "x"));
        assertThrows(NullPointerException.class, () -> new User("x", null));
    }
}
