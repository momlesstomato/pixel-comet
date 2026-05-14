package com.cometproject.storage.mysql.queues.players.objects;

/**
 * Describes player status update behavior for the MySQL storage subsystem.
 */
public class PlayerStatusUpdate {
    private final int playerId;
    private final boolean playerOnline;
    private final String ipAddress;

    /**
     * Creates a player status update instance for the MySQL storage subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param playerOnline Player online supplied by the caller.
     * @param ipAddress Ip address supplied by the caller.
     */
    public PlayerStatusUpdate(int playerId, boolean playerOnline, String ipAddress) {
        this.playerId = playerId;
        this.playerOnline = playerOnline;
        this.ipAddress = ipAddress;
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
     * Indicates whether player online applies to this MySQL storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isPlayerOnline() {
        return playerOnline;
    }

    /**
     * Returns the IP address for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getIpAddress() {
        return this.ipAddress;
    }
}
