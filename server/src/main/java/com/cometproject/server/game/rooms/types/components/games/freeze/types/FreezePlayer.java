package com.cometproject.server.game.rooms.types.components.games.freeze.types;

import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.messages.outgoing.room.freeze.UpdateFreezeLivesMessageComposer;

/**
 * Describes freeze player behavior for the room processing subsystem.
 */
public class FreezePlayer {
    private final PlayerEntity entity;

    private FreezePowerUp powerUp = FreezePowerUp.None;
    private int lives = 3;
    private int balls = 1;

    private int shieldTimer = 0;
    private int freezeTimer = 0;

    /**
     * Creates a freeze player instance for the room processing subsystem.
     *
     * @param playerEntity Player entity supplied by the caller.
     */
    public FreezePlayer(PlayerEntity playerEntity) {
        this.entity = playerEntity;
    }

    /**
     * Executes power up for this room processing contract.
     *
     * @param powerUp Power up supplied by the caller.
     */
    public void powerUp(FreezePowerUp powerUp) {
        switch (powerUp) {
            case Life: {
                this.lives++;

                this.entity.getRoom().getEntities().broadcastMessage(new UpdateFreezeLivesMessageComposer(this.entity.getId(), this.lives));
                return;
            }

            case ExtraBall: {
                this.balls++;
                return;
            }

            case Shield: {
                this.shieldTimer = 10;
                this.entity.applyEffect(new PlayerEffect(this.getEntity().getGameTeam().getTeamId() + 48, 20));
            }
        }

        this.powerUp = powerUp;
    }

    /**
     * Returns the entity for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerEntity getEntity() {
        return entity;
    }

    /**
     * Returns the power up for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public FreezePowerUp getPowerUp() {
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
     */
    public void decrementLives() {
        this.lives--;
    }

    /**
     * Returns the balls for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBalls() {
        return balls;
    }

    /**
     * Updates the balls for this room processing contract.
     *
     * @param balls Balls supplied by the caller.
     */
    public void setBalls(int balls) {
        this.balls = balls;
    }

    /**
     * Executes increment balls for this room processing contract.
     */
    public void incrementBalls() {
        this.balls += 1;
    }

    /**
     * Returns the shield timer for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getShieldTimer() {
        return shieldTimer;
    }

    /**
     * Executes decrement shield timer for this room processing contract.
     */
    public void decrementShieldTimer() {
        this.shieldTimer--;
    }

    /**
     * Returns the freeze timer for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFreezeTimer() {
        return freezeTimer;
    }

    /**
     * Updates the freeze timer for this room processing contract.
     *
     * @param freezeTimer Freeze timer supplied by the caller.
     */
    public void setFreezeTimer(int freezeTimer) {
        this.freezeTimer = freezeTimer;
    }

    /**
     * Executes decrement freeze timer for this room processing contract.
     */
    public void decrementFreezeTimer() {
        this.freezeTimer--;
    }
}
