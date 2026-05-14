package com.cometproject.server.game.rooms.types.components.types;

/**
 * Describes room mute behavior for the room processing subsystem.
 */
public class RoomMute {
    private int playerId;
    private int ticksLeft;

    /**
     * Creates a room mute instance for the room processing subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param ticksLeft Ticks left supplied by the caller.
     */
    public RoomMute(int playerId, int ticksLeft) {
        this.playerId = playerId;
        this.ticksLeft = ticksLeft;
    }

    /**
     * Executes decrease ticks for this room processing contract.
     */
    public void decreaseTicks() {
        this.ticksLeft -= 1;
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
     * Updates the player id for this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the ticks left for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTicksLeft() {
        return ticksLeft;
    }

    /**
     * Updates the ticks left for this room processing contract.
     *
     * @param ticksLeft Ticks left supplied by the caller.
     */
    public void setTicksLeft(int ticksLeft) {
        this.ticksLeft = ticksLeft;
    }
}
