package com.cometproject.server.game.achievements;

import com.cometproject.api.game.achievements.IAchievementsService;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.achievements.types.ITalentTrackLevel;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.queries.achievements.AchievementDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages achievement runtime state for the Comet subsystem.
 */
public class AchievementManager implements IAchievementsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AchievementManager.class);
    private static AchievementManager achievementManager;
    private final Map<AchievementType, IAchievementGroup> achievementGroups;
    private final Map<Integer, Map<AchievementType, IAchievementGroup>> gameCenterAchievements;
    private final Map<Integer, ITalentTrackLevel> talentTrack;

    /**
     * Creates a achievement manager instance for the Comet subsystem.
     */
    public AchievementManager() {
        this.achievementGroups = new ConcurrentHashMap<>();
        this.gameCenterAchievements = new ConcurrentHashMap<>();
        this.talentTrack = new ConcurrentHashMap<>();
    }

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static AchievementManager getInstance() {
        return CometBootstrap.resolve(AchievementManager.class);
    }

    /**
     * Starts this Comet component.
     */
    @Override
    public void start() {
        this.loadAchievements();
        this.loadGameCenterAchievements();
        this.loadTalentTrack();
        LOGGER.info("AchievementManager initialized");
    }

    /**
     * Loads achievements for this Comet contract.
     */
    @Override
    public void loadAchievements() {
        if (this.achievementGroups.size() != 0) {
            for (IAchievementGroup achievementGroup : this.achievementGroups.values()) {
                if (achievementGroup.getAchievements().size() != 0) {
                    achievementGroup.getAchievements().clear();
                }
            }

            this.achievementGroups.clear();
        }

        final int achievementCount = AchievementDao.getAchievements(this.achievementGroups);

        LOGGER.info("Loaded " + achievementCount + " achievements (" + this.achievementGroups.size() + " groups)");

    }

    /**
     * Loads game center achievements for this Comet contract.
     */
    @Override
    public void loadGameCenterAchievements() {
        if(this.gameCenterAchievements.size() != 0) {
            for (Map<AchievementType, IAchievementGroup> achievementGroups : this.gameCenterAchievements.values()) {
                for(IAchievementGroup achievementGroup : achievementGroups.values()) {
                    if (achievementGroup.getAchievements().size() != 0) {
                        achievementGroup.getAchievements().clear();
                    }
                }
            }

            this.gameCenterAchievements.clear();
        }

        final int gameCenterAchievementCount = AchievementDao.getGameCenterAchievements(this.gameCenterAchievements);

        LOGGER.info("Loaded " + gameCenterAchievementCount + " game-center achievements (" + this.gameCenterAchievements.size() + " groups)");
    }

    /**
     * Loads talent track for this Comet contract.
     */
    @Override
    public void loadTalentTrack() {
        if(this.talentTrack.size() != 0) {
            this.talentTrack.clear();
        }

        final int talentTrackCount = AchievementDao.getTalents(this.talentTrack);

        LOGGER.info("Loaded " + talentTrackCount + " talent levels.");
    }

    /**
     * Returns the achievement group for this Comet contract.
     *
     * @param groupName Group name supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IAchievementGroup getAchievementGroup(AchievementType groupName) {
        return this.achievementGroups.get(groupName);
    }

    /**
     * Returns the game center achievement groups for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, Map<AchievementType, IAchievementGroup>> getGameCenterAchievementGroups() {
        return this.gameCenterAchievements;
    }

    /**
     * Returns the talent track for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, ITalentTrackLevel> getTalentTrack() {
        return this.talentTrack;
    }

    /**
     * Returns the achievement groups for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<AchievementType, IAchievementGroup> getAchievementGroups() {
        return this.achievementGroups;
    }
}
