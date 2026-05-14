package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired action bot move behavior for the room subsystem.
 */
public class WiredActionBotMove extends WiredActionItem {
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
    public WiredActionBotMove(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData() == null || this.getWiredData().getSelectedIds() == null || this.getWiredData().getSelectedIds().isEmpty()) {
            return;
        }

        Long itemId = WiredUtil.getRandomElement(this.getWiredData().getSelectedIds());

        if (itemId == null) {
            return;
        }

        RoomItemFloor item = this.getRoom().getItems().getFloorItem(itemId);

        if (item == null || item.isAtDoor() || item.getPosition() == null || item.getTile() == null) {
            return;
        }

        Position position = new Position(item.getPosition().getX(), item.getPosition().getY());

        final String entityName = this.getWiredData().getText();

        final BotEntity botEntity = this.getRoom().getBots().getBotByName(entityName);

        if (botEntity == null) {
            return;
        }

        botEntity.moveTo(position);
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
     * Executes requires player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresPlayer() {
        return true;
    }
}
