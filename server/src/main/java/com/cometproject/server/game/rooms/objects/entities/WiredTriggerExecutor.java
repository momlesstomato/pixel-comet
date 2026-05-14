package com.cometproject.server.game.rooms.objects.entities;

import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.tasks.CometTask;

/**
 * Describes wired trigger executor behavior for the room subsystem.
 */
public class WiredTriggerExecutor<T extends WiredTriggerItem> implements CometTask {
    private final RoomEntity roomEntity;

    private final Class<? extends WiredTriggerItem> triggerClass;
    private final Object data;

    /**
     * Creates a wired trigger executor instance for the room subsystem.
     *
     * @param triggerClass Trigger class supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     */
    public WiredTriggerExecutor(Class<T> triggerClass, RoomEntity entity, Object data) {
        this.roomEntity = entity;
        this.data = data;
        this.triggerClass = triggerClass;

    }

    /**
     * Runs this room task.
     */
    @Override
    public void run() {
        for (RoomItemFloor wiredItem : WiredTriggerItem.getTriggers(this.roomEntity.getRoom(), this.triggerClass)) {
            T trigger = ((T) wiredItem);

            if (trigger.getWiredData().getSelectedIds().contains(((RoomItemFloor) data).getId()))
                trigger.evaluate(this.roomEntity, data);
        }
    }
}
