package com.cometproject.server.network.messages.outgoing.user.profile;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the user badges message for the Pixel Protocol client.
 */
public class UserBadgesMessageComposer extends MessageComposer {
    private final int playerId;
    private final String[] badges;

    /**
     * Creates a user badges message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param badges Badges supplied by the caller.
     */
    public UserBadgesMessageComposer(final int playerId, final String[] badges) {
        this.playerId = playerId;
        this.badges = badges;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.HabboUserBadgesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(playerId);

        int badgeCount = 0;

        for (String badge : this.badges) {
            if (badge != null) {
                badgeCount++;
            }
        }

        msg.writeInt(badgeCount);

        for (int i = 0; i < badges.length; i++) {
            if (badges[i] != null) {
                msg.writeInt(i + 1);
                msg.writeString(badges[i]);
            }
        }
    }
}