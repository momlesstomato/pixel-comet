package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger periodically behavior for the room subsystem.
 */
public class WiredTriggerPeriodically extends WiredTriggerItem {
    private static final int PARAM_TICK_LENGTH = 0;

    private final WiredItemEvent event;

    /**
     * Creates a wired trigger periodically instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerPeriodically(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.getWiredData().getParams().putIfAbsent(PARAM_TICK_LENGTH, 2);

        this.event = new WiredItemEvent(null, null);

        event.setTotalTicks(this.getTickCount());
        this.queueEvent(event);
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean suppliesPlayer() {
        return false;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        this.evaluate(null, null);

        // loop
        this.event.setTotalTicks(this.getTickCount());
        this.queueEvent(this.event);
    }

    /**
     * Handles the data change callback for this room contract.
     */
    @Override
    public void onDataChange() {
        this.event.setTotalTicks(this.getTickCount());
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 6;
    }

    /**
     * Returns the tick count for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTickCount() {
        int tickLength = this.getWiredData().getParams().get(PARAM_TICK_LENGTH);

        if (tickLength <= 0) {
            tickLength = 2;
        }

        return tickLength;
    }
}
