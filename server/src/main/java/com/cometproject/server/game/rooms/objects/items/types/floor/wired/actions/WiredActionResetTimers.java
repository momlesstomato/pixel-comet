package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerAtGivenTime;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerAtGivenTimeLong;
import com.cometproject.server.game.rooms.types.Room;

import java.util.List;


/**
 * Describes wired action reset timers behavior for the room subsystem.
 */
public class WiredActionResetTimers extends WiredActionItem {
    /**
     * The default constructor.
     *
     * @param id       The ID of the item
     * @param itemId   The ID of the item definition
     * @param room     The instance of the room
     * @param owner    The ID of the owner
     * @param x        The position of the item on the X axis
     * @param y        The position of the item on the Y axis
     * @param z        The position of the item on the z axis
     * @param rotation The orientation of the item
     * @param data     The JSON object associated with this item
     */
    public WiredActionResetTimers(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes requires player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresPlayer() {
        return false;
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 1;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        final List<WiredTriggerAtGivenTime> items = this.getRoom().getItems().getByClass(WiredTriggerAtGivenTime.class);

        items.addAll(this.getRoom().getItems().getByClass(WiredTriggerAtGivenTimeLong.class));

        for (WiredTriggerAtGivenTime floorItem : items) {
            floorItem.setNeedsReset(false);
        }

        this.getRoom().resetWiredTimer();
    }
}
