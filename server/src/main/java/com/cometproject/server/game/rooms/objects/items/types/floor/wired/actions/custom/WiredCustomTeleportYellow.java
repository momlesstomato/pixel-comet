package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes wired custom teleport yellow behavior for the room subsystem.
 */
public class WiredCustomTeleportYellow extends WiredActionItem {

    /**
     * Creates a wired custom teleport yellow instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomTeleportYellow(RoomItemData itemData, Room room) {
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
        return 8;
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData().getSelectedIds().size() == 0) return;

        for (long itemId : this.getWiredData().getSelectedIds()) {
            RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem == null) continue;
            List<Integer> groupMembers =  new ArrayList<>(this.getRoom().getGame().getTeams().get(GameTeam.YELLOW));

            for (Integer groupMember : groupMembers) {
                PlayerEntity player = this.getRoom().getEntities().getEntityByPlayerId(groupMember);
                if(player.getPlayer().getEntity().getGameTeam().equals(GameTeam.YELLOW)) { player.teleportToItem(floorItem);} else { return; }
            }
        }
    }
}
