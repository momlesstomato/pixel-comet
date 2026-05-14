package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.boutique.MannequinFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired custom change clothes behavior for the room subsystem.
 */
public class WiredCustomChangeClothes extends WiredActionItem {

    /**
     * Creates a wired custom change clothes instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomChangeClothes(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes requires player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresPlayer() {
        return true;
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
     * Returns the furni selection for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getFurniSelection() {
        return 1;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (!(event.entity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerEntity = ((PlayerEntity) event.entity);

        if (playerEntity.getPlayer() == null || playerEntity.getPlayer().getSession() == null) {
            return;
        }

        for (long itemId : this.getWiredData().getSelectedIds()) {
            final RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (!(floorItem instanceof MannequinFloorItem))
                return;

            MannequinFloorItem mannequinFloorItem = (MannequinFloorItem)floorItem;

            StringBuilder newFigure = new StringBuilder();

            for (String playerFigurePart : playerEntity.getFigure().split("\\.")) {
                if (!playerFigurePart.startsWith("ch") && !playerFigurePart.startsWith("lg"))
                    newFigure.append(playerFigurePart).append(".");
            }

            String newFigureParts = "";

            switch (playerEntity.getGender().toUpperCase()) {
                case "M":
                    newFigureParts = mannequinFloorItem.getFigure();
                    break;

                case "F":
                    newFigureParts = mannequinFloorItem.getFigure();
                    break;
            }

            for (String newFigurePart : newFigureParts.split("\\.")) {
                if (newFigurePart.startsWith("hd"))
                    newFigureParts = newFigureParts.replace(newFigurePart, "");
            }

            if (newFigureParts.equals("")) return;

            final String figure = newFigure + newFigureParts;

            if (figure.length() > 512)
                return;

            playerEntity.getPlayer().getData().setFigure(figure);
            playerEntity.getPlayer().getData().setGender(mannequinFloorItem.getGender());

            playerEntity.getPlayer().getData().save();
            playerEntity.getPlayer().poof();
        }
    }
}
