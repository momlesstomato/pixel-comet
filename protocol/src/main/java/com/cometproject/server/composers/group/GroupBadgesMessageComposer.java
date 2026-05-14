package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the group badges message for the Pixel Protocol client.
 */
public class GroupBadgesMessageComposer extends MessageComposer {
    private final Map<Integer, String> badges;

    private final int groupId;
    private final String badge;

    /**
     * Creates a group badges message composer instance for the protocol composer subsystem.
     *
     * @param badges Badges value supplied by the caller.
     */
    public GroupBadgesMessageComposer(final Map<Integer, String> badges) {
        this.badges = badges;
        this.groupId = 0;
        this.badge = null;
    }

    /**
     * Creates a group badges message composer instance for the protocol composer subsystem.
     *
     * @param groupId Group id value supplied by the caller.
     * @param badge Badge value supplied by the caller.
     */
    public GroupBadgesMessageComposer(final int groupId, final String badge) {
        this.badges = null;
        this.groupId = groupId;
        this.badge = badge;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.HabboGroupBadgesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.badges != null) {
            msg.writeInt(badges.size());

            for (Map.Entry<Integer, String> badge : badges.entrySet()) {
                this.composeGroupBadge(msg, badge.getKey(), badge.getValue());
            }
        } else {
            msg.writeInt(1);

            this.composeGroupBadge(msg, this.groupId, this.badge);
        }
    }

    private void composeGroupBadge(final IComposer msg, final int groupId, final String badge) {
        msg.writeInt(groupId);
        msg.writeString(badge);
    }
}
