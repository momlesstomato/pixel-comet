package com.cometproject.server.game.rooms.objects.entities.effects;

/**
 * Describes player effect behavior for the room subsystem.
 */
public class PlayerEffect {
    private int effectId;
    private int duration;
    private boolean expires;
    private boolean isItemEffect;

    /**
     * Creates a player effect instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     * @param duration Duration supplied by the caller.
     */
    public PlayerEffect(int id, int duration) {
        this.effectId = id;
        this.duration = duration;
        this.expires = duration != 0;
        this.isItemEffect = false;
    }

    /**
     * Creates a player effect instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     */
    public PlayerEffect(int id) {
        this(id, 0);
    }

    /**
     * Creates a player effect instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     * @param isItemEffect Is item effect supplied by the caller.
     */
    public PlayerEffect(int id, boolean isItemEffect) {
        this.effectId = id;
        this.isItemEffect = isItemEffect;
        this.duration = 0;
        this.expires = false;
    }

    /**
     * Returns the effect id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEffectId() {
        return this.effectId;
    }

    /**
     * Returns the duration for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Executes decrement duration for this room contract.
     */
    public void decrementDuration() {
        if (this.duration > 0)
            this.duration--;
    }

    /**
     * Executes expires for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean expires() {
        return this.expires;
    }

    /**
     * Indicates whether item effect applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isItemEffect() {
        return isItemEffect;
    }
}
