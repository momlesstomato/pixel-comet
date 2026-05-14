package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

import java.text.DecimalFormat;


/**
 * Describes magic stack floor item behavior for the room subsystem.
 */
public class MagicStackFloorItem extends RoomItemFloor {
    private double magicHeight = 0d;

    /**
     * Creates a magic stack floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public MagicStackFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        this.getItemData().setData("");
        this.magicHeight = 0d;
        this.saveData();
    }

    /**
     * Returns the override height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public double getOverrideHeight() {
        return magicHeight;
    }

    /**
     * Updates the override height for this room contract.
     *
     * @param magicHeight Magic height supplied by the caller.
     */
    public void setOverrideHeight(double magicHeight) {
        this.getItemData().setData(new DecimalFormat("#.00").format(magicHeight).replace(",", "."));
        this.magicHeight = magicHeight;
    }
}
