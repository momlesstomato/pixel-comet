package com.cometproject.server.game.rooms.objects.items.types.floor.wired.events;

import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;

/**
 * Represents the wired item flash event published by the room subsystem.
 */
public class WiredItemFlashEvent extends WiredItemEvent {

    /**
     * Creates a wired item flash event instance for the room subsystem.
     */
    public WiredItemFlashEvent() {
        super(null, null);

        this.setTotalTicks(2);
    }

    /**
     * Handles the completion callback for this room contract.
     *
     * @param floorItem Floor item supplied by the caller.
     */
    @Override
    public void onCompletion(final RoomItemFloor floorItem) {
        if (floorItem instanceof WiredFloorItem) {
            ((WiredFloorItem) floorItem).switchState();
        }
    }

    /**
     * Indicates whether interactive event applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isInteractiveEvent() {
        return false;
    }
}
