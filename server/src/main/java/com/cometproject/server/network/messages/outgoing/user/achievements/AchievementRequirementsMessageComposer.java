package com.cometproject.server.network.messages.outgoing.user.achievements;

import com.cometproject.api.game.achievements.types.IAchievement;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Collection;

/**
 * Serializes the achievement requirements message for the Pixel Protocol client.
 */
public class AchievementRequirementsMessageComposer extends MessageComposer {

    private final Collection<IAchievementGroup> achievementGroups;

    /**
     * Creates a achievement requirements message composer instance for the network message subsystem.
     *
     * @param achievementGroups Achievement groups supplied by the caller.
     */
    public AchievementRequirementsMessageComposer(Collection<IAchievementGroup> achievementGroups) {
        this.achievementGroups = achievementGroups;
    }


    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BadgeDefinitionsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.achievementGroups.size());

        for (IAchievementGroup achievementGroup : this.achievementGroups) {
            msg.writeString(achievementGroup.getGroupName().replace("ACH_", ""));
            msg.writeInt(achievementGroup.getAchievements().size());

            for (IAchievement achievement : achievementGroup.getAchievements().values()) {
                msg.writeInt(achievement.getLevel());
                msg.writeInt(achievement.getProgressNeeded());
            }
        }
    }
}
