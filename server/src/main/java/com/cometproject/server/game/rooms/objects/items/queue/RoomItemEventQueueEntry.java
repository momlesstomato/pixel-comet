package com.cometproject.server.game.rooms.objects.items.queue;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItem;


/**
 * Describes room item event queue entry behavior for the room subsystem.
 */
public class RoomItemEventQueueEntry {
    private final RoomItem item;
    private final RoomEntity entity;

    private final RoomItemEventType type;

    // optional
    private final int requestData;
    private final boolean isWiredTrigger;

    /**
     * Creates a room item event queue entry instance for the room subsystem.
     *
     * @param item Item supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public RoomItemEventQueueEntry(RoomItem item, RoomItemEventType type) {
        this.item = item;
        this.entity = null;

        this.type = type;

        this.requestData = -1;
        this.isWiredTrigger = false;
    }

    /**
     * Creates a room item event queue entry instance for the room subsystem.
     *
     * @param item Item supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public RoomItemEventQueueEntry(RoomItem item, RoomEntity entity, RoomItemEventType type) {
        this.item = item;
        this.entity = entity;

        this.type = type;

        this.requestData = -1;
        this.isWiredTrigger = false;
    }

    /**
     * Creates a room item event queue entry instance for the room subsystem.
     *
     * @param item Item supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @param type Type supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     */
    public RoomItemEventQueueEntry(RoomItem item, RoomEntity entity, RoomItemEventType type, int requestData, boolean isWiredTrigger) {
        this.item = item;
        this.entity = entity;

        this.type = type;

        this.requestData = requestData;
        this.isWiredTrigger = isWiredTrigger;
    }

    /**
     * Returns the item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomItem getItem() {
        return item;
    }

    /**
     * Returns the entity for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntity getEntity() {
        return entity;
    }

    /**
     * Returns the type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomItemEventType getType() {
        return type;
    }

    /**
     * Returns the request data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRequestData() {
        return requestData;
    }

    /**
     * Indicates whether wired trigger applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isWiredTrigger() {
        return isWiredTrigger;
    }
}
