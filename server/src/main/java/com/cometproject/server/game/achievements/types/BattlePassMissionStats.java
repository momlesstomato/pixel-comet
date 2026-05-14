package com.cometproject.server.game.achievements.types;

/**
 * Describes battle pass mission stats behavior for the Comet subsystem.
 */
public class BattlePassMissionStats {
    public int id;
    public String missionName;
    public int expDone;
    public int expMax;
    public String imageReward;

    /**
     * Creates a battle pass mission stats instance for the Comet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param missionName Mission name supplied by the caller.
     * @param expDone Exp done supplied by the caller.
     * @param expMax Exp max supplied by the caller.
     * @param imageReward Image reward supplied by the caller.
     */
    public BattlePassMissionStats(int id, String missionName, int expDone, int expMax, String imageReward){
        this.id = id;
        this.missionName = missionName;
        this.expDone = expDone;
        this.expMax = expMax;
        this.imageReward = imageReward;
    }
}
