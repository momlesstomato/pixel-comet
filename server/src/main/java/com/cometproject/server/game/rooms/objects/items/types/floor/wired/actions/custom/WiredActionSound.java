package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.network.websockets.packets.outgoing.SurvivalSoundEffectWebPacket;


/**
 * Describes wired action sound behavior for the room subsystem.
 */
public class WiredActionSound extends WiredActionItem {

    /**
     * Creates a wired action sound instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionSound(RoomItemData itemData, Room room) {
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

        String sound = this.getWiredData().getText();

        if(playerEntity.getPlayer().getSession().getWsChannel() == null)
            return;

        WebSocketSessionManager.getInstance().sendMessage(playerEntity.getPlayer().getSession().getWsChannel(),
                new SurvivalSoundEffectWebPacket("survivalSound", sound));
    }
}
