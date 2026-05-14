package com.cometproject.api.game.achievements.types;

import java.util.Set;

/**
 * Defines the i talent track level contract for the Comet subsystem.
 */
public interface ITalentTrackLevel {
    /**
     * Returns the level associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();

    /**
     * Returns the achievements associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<AchievementType> getAchievements();

    /**
     * Returns the items associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getItems();

    /**
     * Returns the badges associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String[] getBadges();

    /**
     * Returns the perks associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String[] getPerks();
}
