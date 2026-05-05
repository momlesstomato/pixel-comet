package com.cometproject.api.game.sso;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents an immutable SSO ticket bound to a specific player and expiry time.
 *
 * @param ticket The opaque ticket token.
 * @param playerId The player identifier allowed to consume the ticket.
 * @param expiresAt The instant at which the ticket expires.
 */
public record SsoTicket(String ticket, int playerId, Instant expiresAt) {
    /**
     * Creates a validated SSO ticket value object.
     *
     * @param ticket The opaque ticket token.
     * @param playerId The player identifier allowed to consume the ticket.
     * @param expiresAt The instant at which the ticket expires.
     * @throws IllegalArgumentException When any field violates the ticket invariants.
     */
    public SsoTicket {
        final String normalizedTicket = Objects.requireNonNull(ticket, "ticket").trim();
        final Instant normalizedExpiresAt = Objects.requireNonNull(expiresAt, "expiresAt");

        if (normalizedTicket.isEmpty() || normalizedTicket.length() < 8 || normalizedTicket.length() > 128) {
            throw new IllegalArgumentException("ticket must be between 8 and 128 characters");
        }

        if (playerId <= 0) {
            throw new IllegalArgumentException("playerId must be greater than zero");
        }

        if (!normalizedExpiresAt.isAfter(Instant.now())) {
            throw new IllegalArgumentException("expiresAt must be in the future");
        }

        ticket = normalizedTicket;
        expiresAt = normalizedExpiresAt;
    }
}