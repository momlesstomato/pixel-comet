package com.cometproject.server.game.achievements.types;

import com.cometproject.api.game.achievements.types.IAchievement;

/**
 * Describes achievement behavior for the Comet subsystem.
 */
public class Achievement implements IAchievement {
    private final int level;
    private final int rewardActivityPoints;
    private final int rewardAchievement;
    private final int rewardType;
    private final int progressNeeded;

    /**
     * Creates a achievement instance for the Comet subsystem.
     *
     * @param level Level supplied by the caller.
     * @param rewardActivityPoints Reward activity points supplied by the caller.
     * @param rewardAchievement Reward achievement supplied by the caller.
     * @param rewardType Reward type supplied by the caller.
     * @param progressNeeded Progress needed supplied by the caller.
     */
    public Achievement(int level, int rewardActivityPoints, int rewardAchievement, int rewardType, int progressNeeded) {
        this.level = level;
        this.rewardActivityPoints = rewardActivityPoints;
        this.rewardAchievement = rewardAchievement;
        this.rewardType = rewardType;
        this.progressNeeded = progressNeeded;
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
     * Returns the reward activity points for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRewardActivityPoints() {
        return rewardActivityPoints;
    }

    /**
     * Returns the reward achievement for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRewardAchievement() {
        return rewardAchievement;
    }

    /**
     * Returns the reward type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRewardType() {
        return rewardType;
    }

    /**
     * Returns the progress needed for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getProgressNeeded() {
        return progressNeeded;
    }
}
