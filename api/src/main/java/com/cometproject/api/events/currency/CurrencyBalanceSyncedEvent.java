package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;

/**
 * Event fired after an immediate purse synchronization was evaluated.
 */
public final class CurrencyBalanceSyncedEvent extends Event {
    private final CurrencyMovementResult movement;
    private final boolean playerOnline;
    private final boolean playerNotified;

    /**
     * Creates a balance-synced event.
     *
     * @param movement       the persisted movement.
     * @param playerOnline   whether the player had an online session.
     * @param playerNotified whether a purse packet was sent.
     */
    public CurrencyBalanceSyncedEvent(
            final CurrencyMovementResult movement,
            final boolean playerOnline,
            final boolean playerNotified) {
        this.movement = movement;
        this.playerOnline = playerOnline;
        this.playerNotified = playerNotified;
    }

    /**
     * Returns the persisted movement.
     *
     * @return the movement.
     */
    public CurrencyMovementResult getMovement() {
        return this.movement;
    }

    /**
     * Returns whether the player had an online session.
     *
     * @return true when the player was online.
     */
    public boolean isPlayerOnline() {
        return this.playerOnline;
    }

    /**
     * Returns whether a purse packet was sent.
     *
     * @return true when the player was notified.
     */
    public boolean isPlayerNotified() {
        return this.playerNotified;
    }
}
