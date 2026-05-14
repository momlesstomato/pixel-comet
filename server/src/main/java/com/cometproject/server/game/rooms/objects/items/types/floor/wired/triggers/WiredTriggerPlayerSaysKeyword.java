package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;


/**
 * Describes wired trigger player says keyword behavior for the room subsystem.
 */
public class WiredTriggerPlayerSaysKeyword extends WiredTriggerItem {
    public static final int PARAM_OWNERONLY = 0;

    /**
     * Creates a wired trigger player says keyword instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerPlayerSaysKeyword(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @param message Message supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(PlayerEntity playerEntity, String message) {
        boolean wasExecuted = false;

        for (RoomItemFloor floorItem : getTriggers(playerEntity.getRoom(), WiredTriggerPlayerSaysKeyword.class)) {
            WiredTriggerPlayerSaysKeyword trigger = ((WiredTriggerPlayerSaysKeyword) floorItem);

            final boolean ownerOnly = trigger.getWiredData().getParams().containsKey(PARAM_OWNERONLY) && trigger.getWiredData().getParams().get(PARAM_OWNERONLY) != 0;
            final boolean isOwner = playerEntity.getPlayerId() == trigger.getRoom().getData().getOwnerId();

            if (!ownerOnly || isOwner) {
                if (!trigger.getWiredData().getText().isEmpty() && message.toLowerCase().equals(trigger.getWiredData().getText().toLowerCase())) {
                    wasExecuted = true;
                    trigger.evaluate(playerEntity, message);
                }
            }
        }

        if (wasExecuted) {
            playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), message));
        }

        return wasExecuted;
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean suppliesPlayer() {
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
}
