package com.cometproject.api.game.furniture.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes crackable reward behavior for the furniture subsystem.
 */
public class CrackableReward {
    private final int hitRequirement;
    private final CrackableRewardType rewardType;
    private final CrackableType crackableType;

    private final String rewardData;
    private final int rewardDataInt;
    private List<Integer> rewardsId = new ArrayList<>();

    /**
     * Creates a crackable reward instance for the furniture subsystem.
     *
     * @param hitRequirement Hit requirement value supplied by the caller.
     * @param rewardType Reward type value supplied by the caller.
     * @param crackableType Crackable type value supplied by the caller.
     * @param rewardData Reward data value supplied by the caller.
     * @param rewardDataInt Reward data int value supplied by the caller.
     */
    public CrackableReward(int hitRequirement, CrackableRewardType rewardType, CrackableType crackableType, String rewardData, int rewardDataInt) {
        this.hitRequirement = hitRequirement;
        this.rewardType = rewardType;
        this.crackableType = crackableType;
        this.rewardData = rewardData;
        this.rewardDataInt = rewardDataInt;
        if(!rewardData.contains(",")) {
            this.rewardsId.add(Integer.valueOf(rewardData));
        } else {
            for(String rewardId : rewardData.split(",")) { this.rewardsId.add(Integer.valueOf(rewardId)); }
        }
    }

    /**
     * Returns the hit requirement for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getHitRequirement() {
        return hitRequirement;
    }

    /**
     * Returns the reward type for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public CrackableRewardType getRewardType() {
        return rewardType;
    }

    /**
     * Returns the crackable type for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public CrackableType getCrackableType() {
        return crackableType;
    }

    /**
     * Returns the rewards id for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Integer> getRewardsId() { return this.rewardsId; }

    /**
     * Returns the random reward for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRandomReward() { return this.getRewardsId().get((int)Math.floor(Math.random() * this.getRewardsId().size())); }

    /**
     * Returns the reward data for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRewardData() {
        return rewardData;
    }

    /**
     * Returns the reward data int for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRewardDataInt() {
        return this.rewardDataInt;
    }
}
