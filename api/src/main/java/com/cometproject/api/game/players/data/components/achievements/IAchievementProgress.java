package com.cometproject.api.game.players.data.components.achievements;

/**
 * Defines the i achievement progress contract for the player subsystem.
 */
public interface IAchievementProgress {
    /**
     * Executes the increase progress operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseProgress(int amount);

    /**
     * Executes the decrease progress operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void decreaseProgress(int amount);

    /**
     * Executes the increase level operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean increaseLevel(int amount);

    /**
     * Returns the level associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();

    /**
     * Returns the progress associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getProgress();

    /**
     * Updates the progress value for this player contract.
     *
     * @param progress Progress value supplied by the caller.
     */
    void setProgress(int progress);
}
