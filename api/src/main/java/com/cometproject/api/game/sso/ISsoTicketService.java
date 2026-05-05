package com.cometproject.api.game.sso;

import java.util.Optional;

/**
 * Coordinates SSO ticket issuance, validation, and post-login session tokens.
 */
public interface ISsoTicketService {
    /**
     * Issues a new SSO ticket bound to the given player and valid for the provided TTL.
     *
     * @param playerId The player identifier allowed to consume the ticket.
     * @param ttlSeconds The ticket time-to-live in seconds.
     * @return The newly created ticket.
     * @throws IllegalArgumentException When the player id or TTL is invalid.
     */
    SsoTicket issue(int playerId, int ttlSeconds);

    /**
        * Atomically consumes the ticket when it exists.
        *
        * @param token The raw ticket string supplied by the client.
        * @return The consumed ticket, or empty when it is missing or expired.
        */
        Optional<SsoTicket> consume(String token);

        /**
     * Atomically consumes the ticket when it exists and belongs to the specified player.
     *
     * @param token The raw ticket string supplied by the client.
     * @param playerId The player identifier attempting to authenticate.
     * @return The consumed ticket, or empty when it is missing, expired, or owned by another player.
     */
    Optional<SsoTicket> consume(String token, int playerId);

    /**
     * Revokes a ticket immediately.
     *
     * @param token The raw ticket string to revoke.
     */
    void revoke(String token);

    /**
     * Issues a longer-lived session token for authenticated side-channel requests.
     *
     * @param playerId The player identifier to bind.
     * @return The newly created session token.
     */
    SsoTicket createSessionToken(int playerId);

    /**
     * Persists a longer-lived session token using a caller-supplied token value.
     *
     * @param playerId The player identifier to bind.
     * @param token The token value to persist for the session.
     * @return The stored session token.
     */
    SsoTicket createSessionToken(int playerId, String token);

    /**
     * Resolves the player identifier for an existing session token.
     *
     * @param token The raw session token string.
     * @return The associated player identifier, or empty when the token is missing or expired.
     */
    Optional<Integer> resolvePlayerId(String token);
}