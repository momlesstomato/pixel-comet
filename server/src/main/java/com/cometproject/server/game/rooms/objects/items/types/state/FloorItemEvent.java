package com.cometproject.server.game.rooms.objects.items.types.state;

import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the floor item event published by the room subsystem.
 */
public abstract class FloorItemEvent {
    private final AtomicInteger ticks;
    private int totalTicks;

    /**
     * Creates a floor item event instance for the room subsystem.
     *
     * @param totalTicks Total ticks supplied by the caller.
     */
    protected FloorItemEvent(int totalTicks) {
        this.ticks = new AtomicInteger(0);
        this.totalTicks = totalTicks;
    }

    /**
     * You can override this to FORCE a callback! (even if the onEventComplete method is overriden)
     */
    public void onCompletion(final RoomItemFloor floorItem) {

    }

    /**
     * Updates the total ticks for this room contract.
     *
     * @param ticks Ticks supplied by the caller.
     */
    public void setTotalTicks(final int ticks) {
        this.ticks.set(0);
        this.totalTicks = ticks;
    }

    /**
     * Executes increment ticks for this room contract.
     */
    public void incrementTicks() {
        this.ticks.incrementAndGet();
    }

    /**
     * Indicates whether finished applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFinished() {
        return this.ticks.get() >= this.totalTicks;
    }

    /**
     * Indicates whether interactive event applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isInteractiveEvent() {
        return true;
    }

    /**
     * Returns the current ticks for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCurrentTicks() {
        return this.ticks.get();
    }
}
