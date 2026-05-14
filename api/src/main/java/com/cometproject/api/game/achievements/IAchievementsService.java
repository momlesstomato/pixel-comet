package com.cometproject.api.game.achievements;

import java.util.Map;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.achievements.types.ITalentTrackLevel;
import com.cometproject.api.utilities.Startable;

/**
 * Defines the i achievements service contract for the Comet subsystem.
 */
public interface IAchievementsService extends Startable {
    /**
     * Loads achievements data for this game domain contract.
     */
    void loadAchievements();
    /**
     * Loads talent track data for this game domain contract.
     */
    void loadTalentTrack();
    /**
     * Loads game center achievements data for this game domain contract.
     */
    void loadGameCenterAchievements();

    /**
     * Returns the achievement group associated with this game domain contract.
     *
     * @param groupName Group name value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IAchievementGroup getAchievementGroup(AchievementType groupName);

    /**
     * Returns the achievement groups associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<AchievementType, IAchievementGroup> getAchievementGroups();

    /**
     * Returns the game center achievement groups associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, Map<AchievementType, IAchievementGroup>> getGameCenterAchievementGroups();

    /**
     * Returns the talent track associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ITalentTrackLevel> getTalentTrack();
}
