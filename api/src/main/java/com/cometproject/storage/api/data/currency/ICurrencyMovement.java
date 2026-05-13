package com.cometproject.storage.api.data.currency;

import java.time.Instant;

/**
 * Represents a persisted currency movement audit entry.
 */
public interface ICurrencyMovement {
    /**
     * Returns the movement id.
     *
     * @return the movement id.
     */
    long getId();

    /**
     * Returns the affected player id.
     *
     * @return the player id.
     */
    int getPlayerId();

    /**
     * Returns the affected currency id.
     *
     * @return the currency id.
     */
    long getCurrencyId();

    /**
     * Returns the affected currency code.
     *
     * @return the currency code.
     */
    String getCurrencyCode();

    /**
     * Returns the operation that produced this movement.
     *
     * @return the movement operation.
     */
    CurrencyOperation getOperation();

    /**
     * Returns the signed balance delta.
     *
     * @return the balance delta.
     */
    long getDelta();

    /**
     * Returns the balance before the movement.
     *
     * @return the old balance.
     */
    long getOldBalance();

    /**
     * Returns the balance after the movement.
     *
     * @return the new balance.
     */
    long getNewBalance();

    /**
     * Returns the actor type that initiated the movement.
     *
     * @return the actor type.
     */
    String getActorType();

    /**
     * Returns the actor id that initiated the movement.
     *
     * @return the actor id, or an empty string when absent.
     */
    String getActorId();

    /**
     * Returns the source subsystem that initiated the movement.
     *
     * @return the source type.
     */
    String getSourceType();

    /**
     * Returns the optional source correlation reference.
     *
     * @return the source reference, or an empty string when absent.
     */
    String getSourceRef();

    /**
     * Returns the human-readable reason for the movement.
     *
     * @return the movement reason.
     */
    String getReason();

    /**
     * Returns when the movement was recorded.
     *
     * @return the creation instant.
     */
    Instant getCreatedAt();
}
