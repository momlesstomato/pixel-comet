package com.cometproject.server.game.achievements.types;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.ITalentTrackLevel;

import java.util.Set;

/**
 * Describes talent track level behavior for the Comet subsystem.
 */
public class TalentTrackLevel implements ITalentTrackLevel {
    private final int level;
    private final Set<AchievementType> achievements;
    private final Set<Integer> items;
    private final String[] perks;
    private final String[] badges;

    /**
     * Creates a talent track level instance for the Comet subsystem.
     *
     * @param level Level supplied by the caller.
     * @param achievements Achievements supplied by the caller.
     * @param items Items supplied by the caller.
     * @param perks Perks supplied by the caller.
     * @param badges Badges supplied by the caller.
     */
    public TalentTrackLevel(int level, Set<AchievementType> achievements, Set<Integer> items, String[] perks, String[] badges) {
        this.level = level;
        this.achievements = achievements;
        this.items = items;
        this.perks = perks;
        this.badges = badges;
    }

    /**
     * Returns the level for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Returns the achievements for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Set<AchievementType> getAchievements() {
        return achievements;
    }

    /**
     * Returns the items for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Set<Integer> getItems() {
        return items;
    }

    /**
     * Returns the badges for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String[] getBadges() {
        return badges;
    }

    /**
     * Returns the perks for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String[] getPerks() {
        return perks;
    }
}
