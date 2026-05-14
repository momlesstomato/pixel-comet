package com.cometproject.server.game.players.components.types.achievements;

import com.cometproject.api.game.players.data.components.achievements.IAchievementProgress;

/**
 * Describes achievement progress behavior for the player subsystem.
 */
public class AchievementProgress implements IAchievementProgress {
    private int level;
    private int progress;

    /**
     * Creates a achievement progress instance for the player subsystem.
     *
     * @param level Level supplied by the caller.
     * @param progress Progress supplied by the caller.
     */
    public AchievementProgress(int level, int progress) {
        this.level = level;
        this.progress = progress;
    }

    /**
     * Executes increase progress for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void increaseProgress(int amount) {
        this.progress += amount;
    }

    /**
     * Executes decrease progress for this player contract.
     *
     * @param difference Difference supplied by the caller.
     */
    public void decreaseProgress(int difference) {
        this.progress -= difference;
    }

    /**
     * Executes increase level for this player contract.
     *
     * @param amount Amount supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean increaseLevel(int amount) {
        if(this.level == amount)
            return true;

        this.level += 1;
        return false;
    }

    /**
     * Returns the level for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the progress for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getProgress() {
        return this.progress;
    }

    /**
     * Updates the progress for this player contract.
     *
     * @param progress Progress supplied by the caller.
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }
}
