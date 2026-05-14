package com.cometproject.api.game.achievements.types;

import java.util.Map;

/**
 * Defines the i achievement group contract for the Comet subsystem.
 */
public interface IAchievementGroup {
    /**
     * Returns the id associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the level count associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevelCount();

    /**
     * Returns the achievement associated with this game domain contract.
     *
     * @param level Level value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IAchievement getAchievement(int level);

    /**
     * Returns the achievements associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IAchievement> getAchievements();

    /**
     * Returns the group name associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getGroupName();

    /**
     * Returns the category associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    AchievementCategory getCategory();
}
