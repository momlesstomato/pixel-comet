package com.cometproject.server.network.messages.outgoing.user.achievements;

import com.cometproject.api.game.achievements.types.IAchievement;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.players.data.components.achievements.IAchievementProgress;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.components.types.achievements.AchievementProgress;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the achievement progress message for the Pixel Protocol client.
 */
public class AchievementProgressMessageComposer extends MessageComposer {

    private final IAchievementGroup achievementGroup;
    private final IAchievementProgress achievementProgress;

    /**
     * Creates a achievement progress message composer instance for the network message subsystem.
     *
     * @param achievementProgress Achievement progress supplied by the caller.
     * @param achievementGroup Achievement group supplied by the caller.
     */
    public AchievementProgressMessageComposer(IAchievementProgress achievementProgress, IAchievementGroup achievementGroup) {
        this.achievementProgress = achievementProgress == null ? null : new AchievementProgress(achievementProgress.getLevel(), achievementProgress.getProgress());
        this.achievementGroup = achievementGroup;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AchievementProgressedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        final IAchievement achievement = this.achievementGroup.getAchievement(this.achievementProgress.getLevel());
        int totalLevels = this.achievementGroup.getLevelCount();

        msg.writeInt(achievementGroup.getId());
        msg.writeInt(achievement == null ? totalLevels : achievement.getLevel());
        msg.writeString(achievementGroup.getGroupName() + (achievement == null ? totalLevels : achievement.getLevel()));
        msg.writeInt(achievement == null ? this.achievementGroup.getLevelCount() : achievement.getLevel() == 1 ? 0 : achievementGroup.getAchievement(achievement.getLevel() - 1).getProgressNeeded());
        msg.writeInt(achievement == null ? this.achievementGroup.getAchievement(totalLevels).getProgressNeeded() : achievement.getProgressNeeded());
        msg.writeInt(achievement == null ? this.achievementGroup.getAchievement(totalLevels).getRewardActivityPoints() : achievement.getRewardActivityPoints());
        msg.writeInt(achievement == null ? this.achievementGroup.getAchievement(totalLevels).getRewardType() : achievement.getRewardType());
        msg.writeInt(achievementProgress.getProgress());

        if (achievementProgress.getLevel() >= achievementGroup.getLevelCount() && achievementProgress.getProgress() == (achievement == null ? this.achievementGroup.getAchievement(totalLevels).getProgressNeeded() : achievement.getProgressNeeded())) {
            msg.writeBoolean(true);
        } else {
            msg.writeBoolean(false);
        }

        msg.writeString(achievementGroup.getCategory().toString().toLowerCase());
        msg.writeString("");
        msg.writeInt(achievementGroup.getLevelCount());
        msg.writeInt(0);
    }
}
