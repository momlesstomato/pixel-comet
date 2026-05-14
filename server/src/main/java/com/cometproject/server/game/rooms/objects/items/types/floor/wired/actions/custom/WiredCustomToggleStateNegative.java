package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.DiceFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * Describes wired custom toggle state negative behavior for the room subsystem.
 */
public class WiredCustomToggleStateNegative extends WiredActionItem {
    /**
     * Creates a wired custom toggle state negative instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomToggleStateNegative(RoomItemData itemData, Room room) {
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
        return 0;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        List<Position> tilesToUpdate = Lists.newArrayList();

        for (long itemId : this.getWiredData().getSelectedIds()) {
            final RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem == null || floorItem instanceof WiredFloorItem || floorItem instanceof DiceFloorItem)
                continue;

            if(floorItem.getItemData().getData().equals("0")) {
                floorItem.getItemData().setData("" + (floorItem.getDefinition().getInteractionCycleCount() - 1));
            } else {
                floorItem.getItemData().setData("" + (Integer.parseInt(floorItem.getItemData().getData()) - 1));
            }

            floorItem.sendUpdate();
            tilesToUpdate.add(new Position(floorItem.getPosition().getX(), floorItem.getPosition().getY()));
        }

        for (Position tileToUpdate : tilesToUpdate) {
            this.getRoom().getMapping().updateTile(tileToUpdate.getX(), tileToUpdate.getY());
        }

        tilesToUpdate.clear();
    }
}