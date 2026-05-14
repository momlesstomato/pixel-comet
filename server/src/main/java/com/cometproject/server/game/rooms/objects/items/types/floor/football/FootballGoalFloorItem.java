package com.cometproject.server.game.rooms.objects.items.types.floor.football;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.RollableFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.avatar.ActionMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes football goal floor item behavior for the room subsystem.
 */
public class FootballGoalFloorItem extends RoomItemFloor {
    private GameTeam gameTeam;

    /**
     * Creates a football goal floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FootballGoalFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        switch (this.getDefinition().getItemName()) {
            case "fball_goal_b":
                this.gameTeam = GameTeam.BLUE;
                break;
            case "fball_goal_r":
                this.gameTeam = GameTeam.RED;
                break;
            case "fball_goal_y":
                this.gameTeam = GameTeam.YELLOW;
                break;
            case "fball_goal_g":
                this.gameTeam = GameTeam.GREEN;
                break;
        }
    }

    /**
     * Handles the item added to stack callback for this room contract.
     *
     * @param floorItem Floor item supplied by the caller.
     */
    @Override
    public void onItemAddedToStack(RoomItemFloor floorItem) {
        if(floorItem instanceof RollableFloorItem) {
            PlayerEntity test = (PlayerEntity) ((FootballFloorItem) floorItem).getPusher();

            if(test == null || test.getPlayer() == null)
                return;

            final int playerId = test.getPlayer().getId();

            if (PlayerManager.getInstance().isOnline(playerId)) {
                Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

                if (session != null && session.getPlayer() != null && session.getPlayer().getAchievements() != null) {
                    session.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_21, 1);
                    this.getRoom().getEntities().broadcastMessage(new ActionMessageComposer(((FootballFloorItem) floorItem).getPusher().getId(), 1));
                }
            }
        }
    }

    /**
     * Returns the game team for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public GameTeam getGameTeam() {
        return gameTeam;
    }
}
