package com.cometproject.server.game.rooms.types.components.games.survival.types;

import com.cometproject.api.config.SurvivalSettings;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.freeze.UpdateFreezeLivesMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Describes survival player behavior for the room processing subsystem.
 */
public class SurvivalPlayer {
    private final Session session;
    private SurvivalPowerUp powerUp = SurvivalPowerUp.None;
    private final List<SurvivalPowerUp> powerUpList = Lists.newArrayList();
    private int lives = 100;
    private int bullets = 0;
    private int kills = 0;
    private int shield = 0;
    private int speedTime = 0;
    private int lastShootTimer = 0;

    /**
     * Creates a survival player instance for the room processing subsystem.
     *
     * @param session Session participating in the operation.
     */
    public SurvivalPlayer(Session session) {
        this.session = session;
    }

    /**
     * Returns the session for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Executes power up for this room processing contract.
     *
     * @param powerUp Power up supplied by the caller.
     * @param oldPowerUp Old power up supplied by the caller.
     * @param isSwap Is swap supplied by the caller.
     */
    public void powerUp(SurvivalPowerUp powerUp, SurvivalPowerUp oldPowerUp, boolean isSwap) {
        boolean needsHold = false;
        int entityId = this.session.getPlayer().getEntity().getId();
        PlayerEntity entity = this.session.getPlayer().getEntity();
        switch (powerUp) {
            case Life:
                if(this.lives > 51){
                    this.lives = 100;
                } else this.lives += 50;

                this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.health.recieved", "You've just taken stamina from the chest! You actually have %life% HP.").replace("%life%", this.getLives() + ""), 0));
                this.session.send(new UpdateFreezeLivesMessageComposer(entityId, this.lives));
                needsHold = true;
                break;

            case Shield:
                if(this.shield < 100) {
                    this.shield = 100;
                } //else this.shield += 10;
                this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.shield.recieved", "You've just taken shield power from the chest! You actually have %shield% PWR.").replace("%shield%", this.getShield() + ""), 0));
                needsHold = true;
                break;

            case Sniper:
                if(!isSwap) {
                    this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.sniper.recieved", "You've just taken a sniper from the chest!").replace("%bullets%", this.getBullets() + ""), 0));
                    this.bullets += SurvivalSettings.sniperBullets;
                }

                entity.applyEffect(new PlayerEffect(587, 0));
                break;

            case Gun:
                if(!isSwap) {
                    this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.shotgun.recieved", "You've just taken a revolver from the chest!").replace("%bullets%", this.getBullets() + ""), 0));
                    this.bullets += SurvivalSettings.gunBullets;
                }
                entity.applyEffect(new PlayerEffect(585, 0));
                break;

            case Bullets:
                this.bullets += SurvivalSettings.chestBullets;
                this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.bullets.recieved", "You've just taken 10 bullets from the chest, you have a total of %bullets% now!").replace("%bullets%", this.getBullets() + ""), 0));
                this.session.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_104, 1);
                needsHold = true;
                break;

            case Speed:
                this.session.getPlayer().getEntity().setFastWalkEnabled(true);
                this.session.send(new WhisperMessageComposer(entityId, Locale.getOrDefault("survival.speed.recieved", "You walk faster during some seconds."), 0));
                this.setSpeedTime(SurvivalSettings.speedBoostTime);
                needsHold = true;
                break;
        }

        if(needsHold) {
            this.powerUp = oldPowerUp;
        } else {
            this.powerUp = powerUp;
            if(!this.powerUpList.contains(SurvivalPowerUp.Gun) && powerUp == SurvivalPowerUp.Gun)
                this.powerUpList.add(powerUp);

            if(!this.powerUpList.contains(SurvivalPowerUp.Sniper) && powerUp == SurvivalPowerUp.Sniper)
                this.powerUpList.add(powerUp);
        }

        String sound;

        if(powerUp == SurvivalPowerUp.Shield){
            sound = "shield";
        } else if(powerUp == SurvivalPowerUp.Life) {
            sound = "bandage";
        } else {
            sound = "br_walk";
        }

        this.session.send(new MassEventMessageComposer("habblet/open/playSound?sound="+ sound + ""));
        this.session.send(new MassEventMessageComposer("habblet/open/survivalHP?damage=0&shield=" + this.getShield() + "&health=" + this.getLives()));
        this.session.send(new MassEventMessageComposer("habblet/open/survivalPower?gun=" + (this.getPowerUpList().contains(SurvivalPowerUp.Gun) ? "yes" : "no") + "&sniper=" + (this.getPowerUpList().contains(SurvivalPowerUp.Sniper) ? "yes" : "no") + "&bandage=no"));
        this.session.send(new MassEventMessageComposer("habblet/open/survivalSync?figure=" + session.getPlayer().getData().getFigure() + "&kills=" + this.getKills() + "&bullets=" + this.getBullets()));
    }

    /**
     * Returns the entity for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerEntity getEntity() {
        return this.session.getPlayer().getEntity();
    }
    /**
     * Returns the power up for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public SurvivalPowerUp getPowerUp() {
        return powerUp;
    }
    /**
     * Returns the lives for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLives() {
        return lives;
    }
    /**
     * Updates the lives for this room processing contract.
     *
     * @param lives Lives supplied by the caller.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    /**
     * Executes decrement lives for this room processing contract.
     *
     * @param lives Lives supplied by the caller.
     */
    public void decrementLives(int lives) {
        this.lives -= lives;
    }
    /**
     * Returns the bullets for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBullets() {
        return bullets;
    }
    /**
     * Returns the shield for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getShield() {
        return shield;
    }
    /**
     * Executes decrement shield for this room processing contract.
     *
     * @param shield Shield supplied by the caller.
     */
    public void decrementShield(int shield) {
        this.shield -= shield;
    }
    /**
     * Returns the kills for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getKills() {
        return kills;
    }
    /**
     * Executes increment kills for this room processing contract.
     */
    public void incrementKills() {
        this.kills++;
    }
    /**
     * Executes reset shield for this room processing contract.
     */
    public void resetShield() { this.shield = 0; }
    /**
     * Executes increment bullets for this room processing contract.
     */
    public void incrementBullets() {
        this.bullets += 1;
    }
    /**
     * Executes increment shield for this room processing contract.
     */
    public void incrementShield(){
        if(this.shield > 51){
            this.shield = 100;
        } else this.shield = 50;
    }
    /**
     * Executes increment bullets for this room processing contract.
     *
     * @param bullets Bullets supplied by the caller.
     */
    public void incrementBullets(int bullets) {
        this.bullets += bullets;
    }
    /**
     * Updates the bullets for this room processing contract.
     *
     * @param bullets Bullets supplied by the caller.
     */
    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
    /**
     * Returns the speed time for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSpeedTime() {
        return speedTime;
    }
    /**
     * Returns the last shoot timer for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastShootTimer() {
        return lastShootTimer;
    }
    /**
     * Updates the last shoot timer for this room processing contract.
     *
     * @param time Time supplied by the caller.
     */
    public void setLastShootTimer(int time) {
        this.lastShootTimer = time;
    }
    /**
     * Updates the speed time for this room processing contract.
     *
     * @param survivalTimer Survival timer supplied by the caller.
     */
    public void setSpeedTime(int survivalTimer) {
        this.speedTime = survivalTimer;
    }
    /**
     * Executes decrement speed time for this room processing contract.
     */
    public void decrementSpeedTime() {
        this.speedTime--;
    }

    /**
     * Returns the power up list for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<SurvivalPowerUp> getPowerUpList(){
        return this.powerUpList;
    }
}
