package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition player has badge equipped behavior for the room subsystem.
 */
public class WiredConditionPlayerHasBadgeEquipped extends WiredConditionItem {

    /**
     * Creates a wired condition player has badge equipped instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionPlayerHasBadgeEquipped(RoomItemData itemData, Room room) {        super(itemData, room);    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 11;
    }

    /**
     * Executes evaluate for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean evaluate(RoomEntity entity, Object data) {
        boolean isEquipped = false;
        if (entity == null || !(entity instanceof PlayerEntity)) return false;

        PlayerEntity playerEntity = ((PlayerEntity) entity);

        if (playerEntity.getPlayer().getInventory().hasBadge(this.getWiredData().getText())) {
            int slot = playerEntity.getPlayer().getInventory().getBadges().get(this.getWiredData().getText());

            if (slot != 0)
                isEquipped = true;
        }

        return isNegative != isEquipped;
    }
}
