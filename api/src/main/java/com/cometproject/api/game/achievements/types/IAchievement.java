package com.cometproject.api.game.achievements.types;

/**
 * Defines the i achievement contract for the Comet subsystem.
 */
public interface IAchievement {
    /**
     * Returns the level associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();

    /**
     * Returns the reward activity points associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRewardActivityPoints();

    /**
     * Returns the reward achievement associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRewardAchievement();

    /**
     * Returns the reward type associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRewardType();

    /**
     * Returns the progress needed associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getProgressNeeded();
}
