package com.cometproject.server.game.rooms.objects.items.types.floor.games;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameType;
import org.apache.commons.lang3.StringUtils;

/**
 * Describes abstract game timer floor item behavior for the room subsystem.
 */
public abstract class AbstractGameTimerFloorItem extends RoomItemFloor {
    public String lastTime;

    /**
     * Creates a abstract game timer floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AbstractGameTimerFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTriggered Is wired triggered supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTriggered) {
        if (!isWiredTriggered) {
            if (!(entity instanceof PlayerEntity)) {
                return false;
            }

            PlayerEntity pEntity = (PlayerEntity) entity;

            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId())
                    && !pEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                return false;
            }
        }

        if (requestData == 2) {
            int time = 0;

            if (!this.getItemData().getData().isEmpty() && StringUtils.isNumeric(this.getItemData().getData())) {
                time = Integer.parseInt(this.getItemData().getData());
            }

            if (time == 0 || time == 30 || time == 60 || time == 120 || time == 180 || time == 300 || time == 600) {
                switch (time) {
                    default:
                        time = 0;
                        break;
                    case 0:
                        time = 30;
                        break;
                    case 30:
                        time = 60;
                        break;
                    case 60:
                        time = 120;
                        break;
                    case 120:
                        time = 180;
                        break;
                    case 180:
                        time = 300;
                        break;
                    case 300:
                        time = 600;
                        break;
                }
            } else {
                time = 0;
            }

            this.getItemData().setData(time + "");
            this.sendUpdate();
            this.saveData();
        } else {
            if (this.getItemData().getData().equals("0") && this.lastTime != null && !this.lastTime.isEmpty()) {
                this.getItemData().setData(this.lastTime);
            }

            int gameLength = Integer.parseInt(this.getItemData().getData());

            this.lastTime = this.getItemData().getData();

            if (gameLength == 0) return true;

            if (this.getRoom().getGame().getInstance() == null) {
                this.getRoom().getGame().createNew(this.getGameType());
                this.getRoom().getGame().getInstance().startTimer(gameLength);
            }
        }

        return true;
    }

    /**
     * Handles the pickup callback for this room contract.
     */
    @Override
    public void onPickup() {
        if (this.getRoom().getGame().getInstance() != null) {
            this.getRoom().getGame().getInstance().onGameEnds();
            this.getRoom().getGame().stop();
        }
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataObject() {
        return this.lastTime != null && !this.lastTime.isEmpty() ? this.lastTime : this.getItemData().getData();
    }

    /**
     * Updates the last time for this room contract.
     *
     * @param lastTime Last time supplied by the caller.
     */
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Returns the game type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract GameType getGameType();
}
