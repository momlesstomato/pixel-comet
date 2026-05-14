package com.cometproject.server.game.rooms.objects.items.types.floor.wired.base;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom.WiredActionCheckIdle;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.data.WiredActionItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.wired.dialog.WiredActionMessageComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * Describes wired action item behavior for the room subsystem.
 */
public abstract class WiredActionItem extends WiredFloorItem {

    /**
     * Creates a wired action item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the dialog for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public MessageComposer getDialog() {
        return new WiredActionMessageComposer(this);
    }

    /**
     * Executes evaluate for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public final boolean evaluate(RoomEntity entity, Object data) {
        if (this.hasTicks() && !(data instanceof WiredActionCheckIdle)) return false;

        final WiredItemEvent itemEvent = new WiredItemEvent(entity, data);

        if(data instanceof WiredActionCheckIdle)
            return true;

        if (this.getWiredData().getDelay() >= 1 && this.usesDelay()) {
            itemEvent.setTotalTicks(RoomItemFactory.getProcessTime(this.getWiredData().getDelay() / 2));
            this.queueEvent(itemEvent);
        } else {
            itemEvent.onCompletion(this);
            this.onEventComplete(itemEvent);
        }

        return true;
    }

    /**
     * Returns the wired data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public WiredActionItemData getWiredData() {
        return (WiredActionItemData) super.getWiredData();
    }

    /**
     * Returns the incompatible triggers for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<WiredTriggerItem> getIncompatibleTriggers() {
        List<WiredTriggerItem> incompatibleTriggers = Lists.newArrayList();

        if (this.requiresPlayer()) {
            for (RoomItemFloor floorItem : this.getItemsOnStack()) {
                if (floorItem instanceof WiredTriggerItem) {
                    if (!((WiredTriggerItem) floorItem).suppliesPlayer()) {
                        incompatibleTriggers.add(((WiredTriggerItem) floorItem));
                    }
                }
            }
        }

        return incompatibleTriggers;
    }

    /**
     * Executes requires player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean requiresPlayer();
}
