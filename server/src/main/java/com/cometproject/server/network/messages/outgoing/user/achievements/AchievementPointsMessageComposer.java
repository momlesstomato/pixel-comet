package com.cometproject.server.network.messages.outgoing.user.achievements;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the achievement points message for the Pixel Protocol client.
 */
public class AchievementPointsMessageComposer extends MessageComposer {
    private final int points;
    private final int level;

    /**
     * Creates a achievement points message composer instance for the network message subsystem.
     *
     * @param points Points supplied by the caller.
     * @param level Level supplied by the caller.
     */
    public AchievementPointsMessageComposer(final int points, final int level) {
        this.points = points;
        this.level = level;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AchievementScoreMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.points);
        msg.writeInt(this.level); // level
    }
}
