package com.cometproject.server.game.rooms.objects.items.types.floor.football;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;


/**
 * Describes football score floor item behavior for the room subsystem.
 */
public class FootballScoreFloorItem extends RoomItemFloor {
    private GameTeam gameTeam;

    /**
     * Creates a football score floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FootballScoreFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.getItemData().setData("0");

        switch (this.getDefinition().getItemName()) {
            case "fball_score_b":
                this.gameTeam = GameTeam.BLUE;
                break;
            case "fball_score_r":
                this.gameTeam = GameTeam.RED;
                break;
            case "fball_score_y":
                this.gameTeam = GameTeam.YELLOW;
                break;
            case "fball_score_g":
                this.gameTeam = GameTeam.GREEN;
                break;
        }
    }

    /**
     * Executes send update for this room contract.
     */
    public void sendUpdate() {
        this.getItemData().setData(this.getRoom().getGame().getScore(this.gameTeam) + "");

        super.sendUpdate();
    }

    /**
     * Executes reset for this room contract.
     */
    public void reset() {
        this.getItemData().setData(0 + "");
        this.sendUpdate();
    }
}
