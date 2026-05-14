package com.cometproject.server.game.rooms.objects.items.types.floor.games.banzai;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.AbstractGameGateFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.GameType;


/**
 * Describes banzai gate floor item behavior for the room subsystem.
 */
public class BanzaiGateFloorItem extends AbstractGameGateFloorItem {
    private GameTeam gameTeam;

    /**
     * Creates a banzai gate floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BanzaiGateFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        switch (this.getDefinition().getInteraction()) {
            case "bb_red_gate":
                gameTeam = GameTeam.RED;
                break;
            case "bb_blue_gate":
                gameTeam = GameTeam.BLUE;
                break;
            case "bb_green_gate":
                gameTeam = GameTeam.GREEN;
                break;
            case "bb_yellow_gate":
                gameTeam = GameTeam.YELLOW;
                break;
        }
    }

    /**
     * Executes game type for this room contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public GameType gameType() {
        return GameType.BANZAI;
    }

    /**
     * Returns the team for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public GameTeam getTeam() {
        return gameTeam;
    }
}
