package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerCollision;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;


/**
 * Describes wired action collide team behavior for the room subsystem.
 */
public class WiredActionCollideTeam extends WiredActionItem {
    private static final int PARAM_TEAM_ID = 0;

    /**
     * Creates a wired action collide team instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionCollideTeam(RoomItemData itemData, Room room) {
        super(itemData, room);

        if (this.getWiredData().getParams().size() != 1) {
            this.getWiredData().getParams().put(PARAM_TEAM_ID, 1); // team red
        }
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
        return 9;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        final GameTeam gameTeam = this.getTeam();

        if (this.getTeam() == GameTeam.NONE)
            return;

        for(PlayerEntity player : this.getRoom().getEntities().getPlayerEntities()){
            if (player.getGameTeam() == gameTeam)
                WiredTriggerCollision.executeTriggers(player, null);
        }
    }

    private GameTeam getTeam() {
        switch (this.getWiredData().getParams().get(PARAM_TEAM_ID)) {

            case 1:
                return GameTeam.RED;
            case 2:
                return GameTeam.GREEN;
            case 3:
                return GameTeam.BLUE;
            case 4:
                return GameTeam.YELLOW;
        }

        return GameTeam.NONE;
    }
}
