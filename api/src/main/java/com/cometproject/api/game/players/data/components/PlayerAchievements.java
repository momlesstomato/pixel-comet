package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.players.data.IPlayerComponent;
import com.cometproject.api.game.players.data.components.achievements.IAchievementProgress;

/**
 * Defines the player achievements contract for the player subsystem.
 */
public interface PlayerAchievements extends IPlayerComponent {

    /**
     * Executes the progress achievement operation for this player contract.
     *
     * @param type Type value supplied by the caller.
     * @param data Data value supplied by the caller.
     */
    void progressAchievement(AchievementType type, int data);

    /**
     * Loads achievements data for this player contract.
     */
    void loadAchievements();

    /**
     * Indicates whether this player contract has started achievement.
     *
     * @param achievementType Achievement type value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasStartedAchievement(AchievementType achievementType);

    /**
     * Returns the progress associated with this player contract.
     *
     * @param achievementType Achievement type value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IAchievementProgress getProgress(AchievementType achievementType);
}
