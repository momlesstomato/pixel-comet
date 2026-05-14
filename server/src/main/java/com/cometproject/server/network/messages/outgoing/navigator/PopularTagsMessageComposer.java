package com.cometproject.server.network.messages.outgoing.navigator;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the popular tags message for the Pixel Protocol client.
 */
public class PopularTagsMessageComposer extends MessageComposer {
    private final Map<String, Integer> popularTags;

    /**
     * Creates a popular tags message composer instance for the network message subsystem.
     *
     * @param popularTags Popular tags supplied by the caller.
     */
    public PopularTagsMessageComposer(final Map<String, Integer> popularTags) {
        this.popularTags = popularTags;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PopularRoomTagsResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(popularTags.size() > 50 ? 50 : popularTags.size());

        for (Map.Entry<String, Integer> entry : popularTags.entrySet()) {
            msg.writeString(entry.getKey());
            msg.writeInt(entry.getValue());
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.popularTags.clear();
    }
}
