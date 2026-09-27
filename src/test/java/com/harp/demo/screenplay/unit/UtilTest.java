package com.harp.demo.screenplay.unit;

import com.harp.demo.screenplay.utils.Util;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility unit tests reported through Serenity (no Appium / no device).
 */
@ExtendWith(SerenityJUnit5Extension.class)
class UtilTest {

    @Test
    @Title("isBlank detects null and whitespace")
    void isBlankDetectsNullAndWhitespace() {
        assertTrue(Util.isBlank(null));
        assertTrue(Util.isBlank("   "));
        assertFalse(Util.isBlank("x"));
    }

    @Test
    @Title("encodeQueryValue encodes spaces")
    void encodeQueryValueEncodesSpaces() {
        assertEquals("hello%20world", Util.encodeQueryValue("hello world"));
    }

    @Test
    @Title("encodeQueryValue rejects null")
    void encodeQueryValueRejectsNull() {
        assertThrows(NullPointerException.class, () -> Util.encodeQueryValue(null));
    }

    @Test
    @Title("maskSecret hides middle characters")
    void maskSecretHidesMiddleCharacters() {
        assertEquals("s****t", Util.maskSecret("secret"));
        assertEquals("", Util.maskSecret(null));
    }
}
