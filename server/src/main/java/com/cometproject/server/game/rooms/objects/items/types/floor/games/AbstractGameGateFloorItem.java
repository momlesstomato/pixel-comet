package com.cometproject.server.game.rooms.objects.items.types.floor.games;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.GameType;


/**
 * Describes abstract game gate floor item behavior for the room subsystem.
 */
public abstract class AbstractGameGateFloorItem extends DefaultFloorItem {
    /**
     * Creates a abstract game gate floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AbstractGameGateFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the load callback for this room contract.
     */
    @Override
    public void onLoad() {
        this.getRoom().getGame().getGates().get(this.getTeam()).add(this);
    }

    /**
     * Handles the unload callback for this room contract.
     */
    @Override
    public void onUnload() {
        this.getRoom().getGame().getGates().get(this.getTeam()).remove(this);
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) return;

        PlayerEntity playerEntity = (PlayerEntity) entity;

        boolean isLeaveTeam = false;

        if (playerEntity.getGameTeam() != GameTeam.NONE && playerEntity.getGameTeam() != this.getTeam()) {
            GameTeam oldTeam = playerEntity.getGameTeam();

            this.getRoom().getGame().removeFromTeam(playerEntity);

            for (AbstractGameGateFloorItem timer : this.getRoom().getGame().getGates().get(this.getTeam())) {
                timer.getItemData().setData(this.getRoom().getGame().getTeams().get(oldTeam).size());
                timer.sendUpdate();
            }

        } else if (playerEntity.getGameTeam() == this.getTeam()) {
            this.getRoom().getGame().removeFromTeam(playerEntity);

            isLeaveTeam = true;
        }

        if (!isLeaveTeam) {
            this.getRoom().getGame().joinTeam(this.getTeam(), playerEntity);

            playerEntity.setGameTeam(this.getTeam());
            playerEntity.applyTeamEffect(new PlayerEffect(this.getTeam().getEffect(this.gameType()), 0));
        } else {
            playerEntity.setGameTeam(GameTeam.NONE);
            playerEntity.applyTeamEffect(null);
        }

        this.updateTeamCount();
    }

    /**
     * Handles the entity leave room callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityLeaveRoom(RoomEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = ((PlayerEntity) entity);

            if (playerEntity.getGameTeam() == this.getTeam()) {
                this.getRoom().getGame().removeFromTeam(playerEntity);
                this.updateTeamCount();
            }
        }
    }

    private void updateTeamCount() {
        this.getItemData().setData("" + this.getRoom().getGame().getTeams().get(this.getTeam()).size());
        this.sendUpdate();
    }

    /**
     * Indicates whether movement cancelled applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isMovementCancelled(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return true;
        }

        final PlayerEntity playerEntity = (PlayerEntity) entity;

        if (this.getRoom().getGame().getInstance() != null && this.getRoom().getGame().getInstance().isActive()) {
            return playerEntity.getGameTeam() == null || playerEntity.getGameTeam() == GameTeam.NONE;
        }

        return false;
    }

    /**
     * Executes game type for this room contract.
     *
     * @return Result produced by the operation.
     */
    public abstract GameType gameType();

    /**
     * Returns the team for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract GameTeam getTeam();
}
