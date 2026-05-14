package com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.AbstractGameGateFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.GameType;

/**
 * Describes freeze gate floor item behavior for the room subsystem.
 */
public class FreezeGateFloorItem extends AbstractGameGateFloorItem {
    private final GameTeam gameTeam;

    /**
     * Creates a freeze gate floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FreezeGateFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        final String itemName = this.getDefinition().getItemName();

        this.getItemData().setData("0");

        if (itemName.endsWith("y")) {
            this.gameTeam = GameTeam.YELLOW;
        } else if (itemName.endsWith("b")) {
            this.gameTeam = GameTeam.BLUE;
        } else if (itemName.endsWith("r")) {
            this.gameTeam = GameTeam.RED;
        } else {
            this.gameTeam = GameTeam.GREEN;
        }
    }

    /**
     * Executes game type for this room contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public GameType gameType() {
        return GameType.FREEZE;
    }

    /**
     * Returns the team for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public GameTeam getTeam() {
        return this.gameTeam;
    }
}
