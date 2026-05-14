package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes wired custom handitem behavior for the room subsystem.
 */
public class WiredCustomHanditem extends WiredActionItem {

    /**
     * Creates a wired custom handitem instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomHanditem(RoomItemData itemData, Room room) {
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
        return 7;
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

        if (this.getWiredData() == null || this.getWiredData().getText() == null) {
            return;
        }

        if (!StringUtils.isNumeric(this.getWiredData().getText())) {
            return;
        }

        int handitemId = Integer.parseInt(this.getWiredData().getText());

        if(playerEntity.getHandItem() == handitemId) {
            playerEntity.carryItem(0, false);
        } else {
            playerEntity.carryItem(handitemId, false);
        }
    }
}
