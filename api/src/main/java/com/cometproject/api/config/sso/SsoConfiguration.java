package com.cometproject.api.config.sso;

import java.util.Map;

/**
 * Declares SSO ticket and session token configuration keys and defaults.
 */
public final class SsoConfiguration {
    public static final String TICKET_TTL_SECONDS = "comet.sso.ticket.ttl.seconds";
    public static final String TICKET_KEY_PREFIX = "comet.sso.ticket.key.prefix";
    public static final String SESSION_TTL_SECONDS = "comet.sso.session.ttl.seconds";
    public static final String SESSION_KEY_PREFIX = "comet.sso.session.key.prefix";

    private SsoConfiguration() {
    }

    /**
     * Returns the default values for the SSO configuration group.
     *
     * @return The default SSO configuration values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                TICKET_TTL_SECONDS, "60",
                TICKET_KEY_PREFIX, "sso",
                SESSION_TTL_SECONDS, "43200",
                SESSION_KEY_PREFIX, "session"
        );
    }
}