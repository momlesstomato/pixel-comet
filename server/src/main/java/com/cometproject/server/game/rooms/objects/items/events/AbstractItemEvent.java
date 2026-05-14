package com.cometproject.server.game.rooms.objects.items.events;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManager;

import java.util.concurrent.TimeUnit;

/**
 * Represents the abstract item event published by the room subsystem.
 */
public abstract class AbstractItemEvent implements CometTask {
    private final RoomItemFloor floorItem;
    private final RoomEntity entity;

    /**
     * Creates a abstract item event instance for the room subsystem.
     *
     * @param floorItem Floor item supplied by the caller.
     * @param entity Entity supplied by the caller.
     */
    public AbstractItemEvent(RoomItemFloor floorItem, RoomEntity entity) {
        this.floorItem = floorItem;
        this.entity = entity;
    }

    /**
     * Executes run in for this room contract.
     *
     * @param seconds Seconds supplied by the caller.
     */
    protected void runIn(double seconds) {
        CometThreadManager.getInstance().executeSchedule(this, (long) (seconds * 1000), TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the floor item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    protected RoomItemFloor getFloorItem() {
        return floorItem;
    }

    /**
     * Returns the entity for this room contract.
     *
     * @return Value exposed by the contract.
     */
    protected RoomEntity getEntity() {
        return entity;
    }
}
