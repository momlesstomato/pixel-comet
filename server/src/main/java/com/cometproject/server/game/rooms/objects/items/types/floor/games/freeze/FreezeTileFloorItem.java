package com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.freeze.FreezeGame;
import com.cometproject.server.game.rooms.types.components.games.freeze.types.FreezePlayer;
import com.cometproject.server.game.rooms.types.mapping.RoomEntityMovementNode;

/**
 * Describes freeze tile floor item behavior for the room subsystem.
 */
public class FreezeTileFloorItem extends RoomItemFloor {
    /**
     * Creates a freeze tile floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FreezeTileFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        if (isWiredTrigger) {
            return false;
        }

        if (entity.getTile() != this.getTile()) {
            double distance = entity.getPosition().distanceTo(this.getPosition());

            if (this.getTile().getMovementNode() != RoomEntityMovementNode.OPEN || distance > 1) {
                return false;
            }

            entity.moveTo(this.getPosition());
        }

        final RoomGame game = this.getRoom().getGame().getInstance();

        if (this.getRoom().getGame().getInstance() == null || !(game instanceof FreezeGame)) {
            return false;
        }

        final PlayerEntity playerEntity = (PlayerEntity) entity;
        final FreezeGame freezeGame = (FreezeGame) game;

        if (playerEntity.getGameTeam() == null || playerEntity.getGameTeam() == GameTeam.NONE) {
            return false;
        }

        final FreezePlayer freezePlayer = freezeGame.freezePlayer(playerEntity.getPlayerId());

        if (freezePlayer.getBalls() < 1) {
            return false;
        }

        freezePlayer.setBalls(freezePlayer.getBalls() - 1);
        freezeGame.launchBall(this, freezePlayer);
        return true;
    }
}
