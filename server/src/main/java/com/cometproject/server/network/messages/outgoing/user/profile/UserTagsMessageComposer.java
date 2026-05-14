package com.cometproject.server.network.messages.outgoing.user.profile;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the user tags message for the Pixel Protocol client.
 */
public class UserTagsMessageComposer extends MessageComposer {
    private final int playerId;
    private final List<String> tags;

    /**
     * Creates a user tags message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param tags Tags supplied by the caller.
     */
    public UserTagsMessageComposer(final int playerId, final List<String> tags) {
        this.playerId = playerId;
        this.tags = tags;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserTagsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(tags.size());

        tags.forEach((tag) -> {
            msg.writeString(tag);
        });
    }
}
