package com.cometproject.server.game.rooms.types.components.games.freeze.types;

import com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze.FreezeTileFloorItem;

/**
 * Describes freeze ball behavior for the room processing subsystem.
 */
public class FreezeBall {
    public static final int START_TICKS = 2;

    private final int playerId;
    private final FreezeTileFloorItem source;
    private final int range;
    private final boolean diagonal;

    private int ticksUntilExplode;

    /**
     * Creates a freeze ball instance for the room processing subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param source Source supplied by the caller.
     * @param range Range supplied by the caller.
     * @param diagonal Diagonal supplied by the caller.
     */
    public FreezeBall(int playerId, FreezeTileFloorItem source, int range, boolean diagonal) {
        this.playerId = playerId;
        this.source = source;
        this.range = range;
        this.diagonal = diagonal;

        // default ticks = 4
        this.ticksUntilExplode = 2;
    }

    /**
     * Returns the player id for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the source for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public FreezeTileFloorItem getSource() {
        return source;
    }

    /**
     * Indicates whether mega applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isMega() {
        return this.range == 999;
    }

    /**
     * Returns the range for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRange() {
        return range;
    }

    /**
     * Indicates whether diagonal applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDiagonal() {
        return diagonal;
    }

    /**
     * Returns the ticks until explode for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTicksUntilExplode() {
        return ticksUntilExplode;
    }

    /**
     * Executes decrement ticks until explode for this room processing contract.
     */
    public void decrementTicksUntilExplode() {
        this.ticksUntilExplode--;
    }
}
