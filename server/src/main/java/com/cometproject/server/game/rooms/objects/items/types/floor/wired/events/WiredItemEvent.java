package com.cometproject.server.game.rooms.objects.items.types.floor.wired.events;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.state.FloorItemEvent;

/**
 * Represents the wired item event published by the room subsystem.
 */
public class WiredItemEvent extends FloorItemEvent {
    public int type = 0;
    public RoomEntity entity;
    public Object data;

    /**
     * Creates a wired item event instance for the room subsystem.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     */
    public WiredItemEvent(RoomEntity entity, Object data) {
        super(0);

        this.entity = entity;
        this.data = data;
    }

    /**
     * Handles the completion callback for this room contract.
     *
     * @param floor Floor supplied by the caller.
     */
    @Override
    public void onCompletion(RoomItemFloor floor) {
        if (floor instanceof WiredActionItem) {
            ((WiredFloorItem) floor).flash();
        }
    }
}
