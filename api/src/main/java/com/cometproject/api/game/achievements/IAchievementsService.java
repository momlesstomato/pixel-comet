package com.cometproject.api.game.achievements;

import java.util.Map;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.achievements.types.ITalentTrackLevel;
import com.cometproject.api.utilities.Startable;

public interface IAchievementsService extends Startable {
    void loadAchievements();
    void loadTalentTrack();
    void loadGameCenterAchievements();

    IAchievementGroup getAchievementGroup(AchievementType groupName);

    Map<AchievementType, IAchievementGroup> getAchievementGroups();

    Map<Integer, Map<AchievementType, IAchievementGroup>> getGameCenterAchievementGroups();

    Map<Integer, ITalentTrackLevel> getTalentTrack();
}
