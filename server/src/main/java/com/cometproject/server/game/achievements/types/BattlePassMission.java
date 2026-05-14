package com.cometproject.server.game.achievements.types;

/**
 * Describes battle pass mission behavior for the Comet subsystem.
 */
public class BattlePassMission {
    public int id;
    public String missionName;
    public int maxExp;
    public BattlePassMissionEnums.MissionType type;
    public BattlePassRewardEnum.RewardType rewardType;
    public String rewardReference;
    public String imageReward;

    /**
     * Creates a battle pass mission instance for the Comet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param missionName Mission name supplied by the caller.
     * @param maxExp Max exp supplied by the caller.
     * @param type Type supplied by the caller.
     * @param rewardType Reward type supplied by the caller.
     * @param rewardReference Reward reference supplied by the caller.
     * @param imageReward Image reward supplied by the caller.
     */
    public BattlePassMission(int id, String missionName, int maxExp, BattlePassMissionEnums.MissionType type, BattlePassRewardEnum.RewardType rewardType, String rewardReference, String imageReward){
        this.id = id;
        this.missionName = missionName;
        this.maxExp = maxExp;
        this.type = type;
        this.rewardType = rewardType;
        this.rewardReference = rewardReference;
        this.imageReward = imageReward;
    }
}
