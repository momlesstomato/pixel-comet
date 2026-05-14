package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;


/**
 * Describes wired action show message behavior for the room subsystem.
 */
public class WiredActionShowMessage extends WiredActionItem {

    protected boolean isWhisperBubble = false;


    /**
     * Creates a wired action show message instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionShowMessage(RoomItemData itemData, Room room) {
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

        String finalText = this.getWiredData().getText();
        finalText = finalText.replace("%username%", playerEntity.getPlayer().getData().getUsername());
        finalText = finalText.replace("%roomname%", getRoom().getData().getName());
        finalText = finalText.replace("%usersonline%", Integer.toString(Comet.getStats().getPlayers()));
        finalText = finalText.replace("%roomcount%", Integer.toString(getRoom().getEntities().getPlayerEntities().size()));

        playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getId(), finalText, isWhisperBubble ? 0 : 34));
    }
}
