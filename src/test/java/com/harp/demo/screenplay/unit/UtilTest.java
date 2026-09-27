package com.harp.demo.screenplay.unit;

import com.harp.demo.screenplay.utils.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilTest {

    @Test
    void isBlankDetectsNullAndWhitespace() {
        assertTrue(Util.isBlank(null));
        assertTrue(Util.isBlank("   "));
        assertFalse(Util.isBlank("x"));
    }

    @Test
    void encodeQueryValueEncodesSpaces() {
        assertEquals("hello%20world", Util.encodeQueryValue("hello world"));
    }

    @Test
    void encodeQueryValueRejectsNull() {
        assertThrows(NullPointerException.class, () -> Util.encodeQueryValue(null));
    }

    @Test
    void maskSecretHidesMiddleCharacters() {
        assertEquals("s****t", Util.maskSecret("secret"));
        assertEquals("", Util.maskSecret(null));
    }
}
