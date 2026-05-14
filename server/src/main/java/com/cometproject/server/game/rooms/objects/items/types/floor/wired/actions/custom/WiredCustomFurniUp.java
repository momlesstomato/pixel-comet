package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.SlideObjectBundleMessageComposer;
import org.apache.commons.lang3.StringUtils;

/**
 * Describes wired custom furni up behavior for the room subsystem.
 */
public class WiredCustomFurniUp extends WiredActionItem {
    protected boolean isUp;

    /**
     * Creates a wired custom furni up instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomFurniUp(RoomItemData itemData, Room room) {
        super(itemData, room);
        this.isUp = true;
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
        return 22;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData() == null || this.getWiredData().getText() == null) {
            return;
        }

        if (!StringUtils.isNumeric(this.getWiredData().getText())) {
            return;
        }

        int newZ = 0;

        try {
            newZ = Integer.parseInt(this.getWiredData().getText());
        } catch (NumberFormatException ignored){}

        for (long itemId : this.getWiredData().getSelectedIds()) {
            final RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem == null)
                continue;

            if(!this.isUp && floorItem.getPosition().getZ() - newZ < 0)
                continue;

            Position oldPos = floorItem.getPosition();
            Position newPos = new Position(floorItem.getPosition().getX(), floorItem.getPosition().getY(), this.isUp ? floorItem.getPosition().getZ() + newZ : floorItem.getPosition().getZ() - newZ);

            this.getRoom().getItems().moveFloorItem(floorItem.getId(), newPos, floorItem.getRotation(), true, false, null, false);
            this.getRoom().getEntities().broadcastMessage(new SlideObjectBundleMessageComposer(oldPos, newPos, 0,0, floorItem.getVirtualId()));
            floorItem.sendUpdate();
        }
    }
}
