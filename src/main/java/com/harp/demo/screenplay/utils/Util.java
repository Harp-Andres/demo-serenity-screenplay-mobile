package com.harp.demo.screenplay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Pure helpers shared by Screenplay tasks and step definitions (unit-testable, no WebDriver).
 */
public final class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String encodeQueryValue(String value) {
        Objects.requireNonNull(value, "value");
        String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8);
        String normalized = encoded.replace("+", "%20");
        LOG.debug("Encoded query fragment for length={}", value.length());
        return normalized;
    }

    public static String maskSecret(String secret) {
        if (isBlank(secret)) {
            return "";
        }
        if (secret.length() <= 2) {
            return "**";
        }
        return secret.charAt(0) + "*".repeat(secret.length() - 2) + secret.charAt(secret.length() - 1);
    }
}
