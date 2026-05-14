package com.cometproject.storage.mysql.queues.players.objects;

/**
 * Describes player badge update behavior for the MySQL storage subsystem.
 */
public class PlayerBadgeUpdate {

    private final int playerId;
    private final String badgeId;
    private final int slot;

    /**
     * Creates a player badge update instance for the MySQL storage subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param badgeId Badge id supplied by the caller.
     * @param slot Slot supplied by the caller.
     */
    public PlayerBadgeUpdate(int playerId, String badgeId, int slot) {
        this.playerId = playerId;
        this.badgeId = badgeId;
        this.slot = slot;
    }

    /**
     * Returns the player id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the badge id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBadgeId() {
        return badgeId;
    }

    /**
     * Returns the slot for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSlot() {
        return slot;
    }
}
