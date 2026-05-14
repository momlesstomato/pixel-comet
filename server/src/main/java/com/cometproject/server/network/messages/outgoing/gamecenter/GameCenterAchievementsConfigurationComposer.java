package com.cometproject.server.network.messages.outgoing.gamecenter;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.IAchievement;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.players.data.components.achievements.IAchievementProgress;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.achievements.AchievementManager;
import com.cometproject.server.game.players.components.AchievementComponent;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes game center achievements configuration composer behavior for the network message subsystem.
 */
public class GameCenterAchievementsConfigurationComposer extends MessageComposer {
    private final int gameId;
    private final AchievementComponent playerAchivements;
    private final Map<AchievementType, IAchievementGroup> gameAchievements;

    /**
     * Creates a game center achievements configuration composer instance for the network message subsystem.
     *
     * @param gameId Game id supplied by the caller.
     * @param playerAchievements Player achievements supplied by the caller.
     */
    public GameCenterAchievementsConfigurationComposer(int gameId, AchievementComponent playerAchievements){
        this.gameId = gameId;
        this.playerAchivements = playerAchievements;
        Map<AchievementType, IAchievementGroup> gameAch = AchievementManager.getInstance().getGameCenterAchievementGroups().get(gameId);
        this.gameAchievements = gameAch == null ? new HashMap<>() : gameAch;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GameAchievementsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameId);

        msg.writeInt(this.gameAchievements.size()); //count
        int count = 0;

        for (Map.Entry<AchievementType, IAchievementGroup> entry : this.gameAchievements.entrySet()) {
            IAchievementProgress achievementProgress = this.playerAchivements.getProgress(entry.getKey());
            IAchievement achievement = achievementProgress == null ? entry.getValue().getAchievement(1) : entry.getValue().getAchievement(achievementProgress.getLevel());

            int actualLevel = achievement.getLevel();
            int actualProgress = achievementProgress == null ? 0 : achievementProgress.getProgress();

            msg.writeInt(count++);
            msg.writeInt(actualLevel);
            msg.writeString(entry.getKey().getGroupName()  + actualLevel);
            msg.writeInt(achievement.getProgressNeeded());
            msg.writeInt(achievement.getProgressNeeded());
            msg.writeInt(achievement.getRewardActivityPoints());
            msg.writeInt(achievement.getRewardAchievement());
            msg.writeInt(actualProgress);
            msg.writeBoolean(actualProgress >= achievement.getProgressNeeded());
            msg.writeString(entry.getKey().getGroupName());
            msg.writeString("basejump");
            msg.writeInt(0);
            msg.writeInt(0);

            //System.out.print("Handled achievement: " + entry.getKey().getGroupName() + achievement.getLevel() + "\n");
        }

        msg.writeString("");
    }
}