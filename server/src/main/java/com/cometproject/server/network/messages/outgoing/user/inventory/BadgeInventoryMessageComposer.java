package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.HashMap;
import java.util.Map;


/**
 * Serializes the badge inventory message for the Pixel Protocol client.
 */
public class BadgeInventoryMessageComposer extends MessageComposer {

    private final Map<String, Integer> badges;

    /**
     * Creates a badge inventory message composer instance for the network message subsystem.
     *
     * @param badges Badges supplied by the caller.
     */
    public BadgeInventoryMessageComposer(final Map<String, Integer> badges) {
        this.badges = badges;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BadgesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        Map<String, Integer> activeBadges = new HashMap<>();

        msg.writeInt(badges.size());

        badges.forEach((badge, slot) -> {
            if (slot > 0) {
                activeBadges.put(badge, slot);
            }

            msg.writeInt(0);
            msg.writeString(badge);
        });

        msg.writeInt(activeBadges.size());

        activeBadges.forEach((k, v) -> {
            msg.writeInt(v);
            msg.writeString(k);
        });

        activeBadges.clear();
    }
}
