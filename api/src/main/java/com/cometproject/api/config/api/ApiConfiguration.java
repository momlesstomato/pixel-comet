package com.cometproject.api.config.api;

import java.util.Map;

/**
 * Declares management API configuration keys and defaults.
 */
public final class ApiConfiguration {
    public static final String ENABLED = "comet.api.enabled";
    public static final String TOKEN = "comet.api.token";
    public static final String TOKEN_HEADER = "comet.api.token.header";
    public static final String DOCS_ENABLED = "comet.api.docs.enabled";

    private ApiConfiguration() {
    }

    /**
     * Returns the default values for the management API configuration group.
     *
     * @return The default management API values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                ENABLED, "false",
                TOKEN, "replace_with_output_of_openssl_rand_hex_32",
                TOKEN_HEADER, "auth_token",
                DOCS_ENABLED, "false"
        );
    }
}