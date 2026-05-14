package com.cometproject.server.game.rooms.objects.items.types.floor.games.banzai;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.AbstractGameTimerFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameType;


/**
 * Describes banzai timer floor item behavior for the room subsystem.
 */
public class BanzaiTimerFloorItem extends AbstractGameTimerFloorItem {

    /**
     * Creates a banzai timer floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BanzaiTimerFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the game type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public GameType getGameType() {
        return GameType.BANZAI;
    }
}
