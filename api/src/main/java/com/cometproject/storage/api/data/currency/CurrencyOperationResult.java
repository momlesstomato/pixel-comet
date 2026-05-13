package com.cometproject.storage.api.data.currency;

/**
 * Describes a persisted currency operation and its runtime synchronization outcome.
 */
public final class CurrencyOperationResult {
    private final CurrencyMovementResult movement;
    private final boolean onlinePlayerUpdated;
    private final boolean playerNotified;

    /**
     * Creates a currency operation result.
     *
     * @param movement            the persisted movement record.
     * @param onlinePlayerUpdated whether an online player's in-memory balance snapshot was updated.
     * @param playerNotified      whether a purse packet was sent to the online player.
     */
    public CurrencyOperationResult(
            final CurrencyMovementResult movement,
            final boolean onlinePlayerUpdated,
            final boolean playerNotified) {
        this.movement = movement;
        this.onlinePlayerUpdated = onlinePlayerUpdated;
        this.playerNotified = playerNotified;
    }

    /**
     * Returns the persisted movement record.
     *
     * @return the movement record.
     */
    public CurrencyMovementResult getMovement() {
        return this.movement;
    }

    /**
     * Returns whether the online player snapshot was updated.
     *
     * @return true when the online snapshot changed.
     */
    public boolean isOnlinePlayerUpdated() {
        return this.onlinePlayerUpdated;
    }

    /**
     * Returns whether the player received an immediate purse packet.
     *
     * @return true when a packet was sent.
     */
    public boolean isPlayerNotified() {
        return this.playerNotified;
    }
}
