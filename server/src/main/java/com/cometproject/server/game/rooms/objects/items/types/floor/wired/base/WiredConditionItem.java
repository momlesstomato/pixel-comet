package com.cometproject.server.game.rooms.objects.items.types.floor.wired.base;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.wired.dialog.WiredConditionMessageComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Describes wired condition item behavior for the room subsystem.
 */
public abstract class WiredConditionItem extends WiredFloorItem {
    protected boolean isNegative;

    /**
     * Creates a wired condition item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.isNegative = this.getClass().getSimpleName().startsWith("WiredNegativeCondition");
    }

    /**
     * Returns the dialog for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public MessageComposer getDialog() {
        return new WiredConditionMessageComposer(this);
    }
}
