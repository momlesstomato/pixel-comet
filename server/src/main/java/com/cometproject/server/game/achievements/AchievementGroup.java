package com.cometproject.server.game.achievements;

import com.cometproject.api.game.achievements.types.AchievementCategory;
import com.cometproject.api.game.achievements.types.IAchievement;
import com.cometproject.api.game.achievements.types.IAchievementGroup;

import java.util.Map;

/**
 * Describes achievement group behavior for the Comet subsystem.
 */
public class AchievementGroup implements IAchievementGroup {
    private Map<Integer, IAchievement> achievements;

    private int id;
    private String groupName;
    private AchievementCategory category;

    /**
     * Creates a achievement group instance for the Comet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param achievements Achievements supplied by the caller.
     * @param groupName Group name supplied by the caller.
     * @param category Category supplied by the caller.
     */
    public AchievementGroup(int id, Map<Integer, IAchievement> achievements, String groupName, AchievementCategory category) {
        this.id = id;
        this.achievements = achievements;
        this.groupName = groupName;
        this.category = category;
    }

    /**
     * Returns the id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns the level count for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLevelCount() {
        return this.achievements.size();
    }

    /**
     * Returns the achievement for this Comet contract.
     *
     * @param level Level supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IAchievement getAchievement(int level) {
        return this.achievements.get(level);
    }

    /**
     * Returns the achievements for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, IAchievement> getAchievements() {
        return achievements;
    }

    /**
     * Returns the group name for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getGroupName() {
        return groupName;
    }

    /**
     * Returns the category for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public AchievementCategory getCategory() {
        return category;
    }
}
