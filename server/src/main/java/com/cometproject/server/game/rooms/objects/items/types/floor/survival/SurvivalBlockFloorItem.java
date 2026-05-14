package com.cometproject.server.game.rooms.objects.items.types.floor.survival;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.survival.SurvivalGame;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalPlayer;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalPowerUp;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;

/**
 * Describes survival block floor item behavior for the room subsystem.
 */
public class SurvivalBlockFloorItem extends RoomItemFloor {

    private boolean destroyed = false;
    private SurvivalPowerUp powerUp;

    /**
     * Creates a survival block floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public SurvivalBlockFloorItem(RoomItemData itemData, Room room) {
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
        if (!(entity instanceof PlayerEntity) || !this.getItemData().getData().equals("0")) {
            return false;
        }

        if(this.getRoom().getGame().getInstance() == null) {
            return false;
        }

        double distance = entity.getPosition().distanceTo(this.getPosition());

        if (distance > 2) {
            entity.moveTo(this.getPosition().squareInFront(this.getRotation()));
            return false;
        }

        ((PlayerEntity) entity).getPlayer().getSession().send(new MassEventMessageComposer("habblet/open/playSound?sound=br_open"));
        ((PlayerEntity) entity).getPlayer().getAchievements().progressAchievement(AchievementType.ACH_105, 1);

        explode();
        return true;
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        final PlayerEntity playerEntity = ((PlayerEntity) entity);

        if(!this.isDestroyed() || this.getPowerUp() == SurvivalPowerUp.None)
            return;

        final RoomGame game = this.getRoom().getGame().getInstance();

        if(this.getRoom().getGame().getInstance() == null || !(game instanceof SurvivalGame)) {
            return;
        }

        final SurvivalGame survivalGame = (SurvivalGame) game;

        final SurvivalPlayer survivalPlayer = survivalGame.survivalPlayer(playerEntity.getPlayerId());

        if(survivalPlayer == null)
            return;

        survivalPlayer.powerUp(this.powerUp, survivalPlayer.getPowerUp(), false);
        // TODO: ACHIEVEMENTsurvivalPlayer.getEntity().getPlayer().getAchievements().progressAchievement(AchievementType.ACH_78, 1);
        this.setPowerUp(SurvivalPowerUp.None);
    }

    private void setPowerUp(SurvivalPowerUp powerUp) {
        this.powerUp = powerUp;
        this.updateState(powerUp, powerUp == SurvivalPowerUp.None && this.isDestroyed());
    }

    private SurvivalPowerUp getPowerUp(){
        return powerUp;
    }

    /**
     * Executes reset for this room contract.
     */
    public void reset() {
        this.destroyed = false;
        this.powerUp = null;

        this.getItemData().setData("0");
        this.sendUpdate();

        this.getTile().reload();
    }

    private void explode() {
        if(this.destroyed) {
            return;
        }

        this.destroyed = true;
        this.setPowerUp(SurvivalPowerUp.getRandom());
        this.getTile().reload();
    }

    private void updateState(SurvivalPowerUp powerUp, boolean fadeAway) {
        int num = 0;
        if (powerUp != null) {
            num = (powerUp.ordinal() + 1) * 1000;
            if (fadeAway) {
                num += 10000;
            }
        }

        this.getItemData().setData(num);
        this.sendUpdate();
    }

    /**
     * Indicates whether destroyed applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
