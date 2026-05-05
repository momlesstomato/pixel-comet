package com.cometproject.server.game.sso.exceptions;

/**
 * Indicates that the SSO Redis backend is not available for ticket operations.
 */
public final class SsoBackendUnavailableException extends RuntimeException {
    /**
     * Creates a new backend unavailable exception with a fixed diagnostic message.
     */
    public SsoBackendUnavailableException() {
        super("SSO backend is unavailable");
    }
}