package com.cometproject.server.game.rooms.objects.items.events.types;

import com.cometproject.server.game.rooms.objects.items.types.state.FloorItemEvent;

/**
 * Represents the roller floor item event published by the room subsystem.
 */
public class RollerFloorItemEvent extends FloorItemEvent {
    /**
     * Creates a roller floor item event instance for the room subsystem.
     *
     * @param totalTicks Total ticks supplied by the caller.
     */
    public RollerFloorItemEvent(int totalTicks) {
        super(totalTicks);
    }
}
