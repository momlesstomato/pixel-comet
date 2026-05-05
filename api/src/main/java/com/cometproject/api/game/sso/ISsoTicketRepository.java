package com.cometproject.api.game.sso;

import java.util.Optional;

/**
 * Provides durable storage for pending SSO tickets.
 *
 * <p>Implementations must be safe for concurrent access and must guarantee
 * atomic consume semantics: a ticket returned by {@link #consume(String)} must
 * not be returned by any subsequent call.
 */
public interface ISsoTicketRepository {
    /**
     * Persists a new ticket entry.
     *
     * @param ticket The ticket to store.
     */
    void save(SsoTicket ticket);

    /**
     * Atomically retrieves and deletes the ticket for the given token string.
     *
     * @param token The raw ticket string sent by the client.
     * @return The stored ticket, or empty when the ticket is missing or already consumed.
     */
    Optional<SsoTicket> consume(String token);

    /**
     * Checks whether a ticket exists without consuming it.
     *
     * @param token The raw ticket string.
     * @return True when the ticket exists and is still valid.
     */
    boolean exists(String token);

    /**
     * Removes an existing ticket immediately.
     *
     * @param token The raw ticket string.
     */
    void revoke(String token);
}