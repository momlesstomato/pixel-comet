package com.cometproject.server.game.rooms.types.components.types;

/**
 * Describes room ban behavior for the room processing subsystem.
 */
public class RoomBan {
    private int playerId;
    private String playerName;
    private int expireTimestamp;

    private boolean isPermanent;

    /**
     * Creates a room ban instance for the room processing subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param playerName Player name supplied by the caller.
     * @param expireTimestamp Expire timestamp supplied by the caller.
     */
    public RoomBan(int playerId, String playerName, int expireTimestamp) {
        this.playerId = playerId;
        this.expireTimestamp = expireTimestamp;
        this.playerName = playerName;

        this.isPermanent = this.expireTimestamp == -1;
    }

    /**
     * Returns the player id for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the expire timestamp for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExpireTimestamp() {
        return expireTimestamp;
    }

    /**
     * Indicates whether permanent applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**
     * Returns the player name for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPlayerName() {
        return playerName;
    }
}
