package com.cometproject.server.network.messages.outgoing.room.filter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Set;

/**
 * Serializes the get room filter list message for the Pixel Protocol client.
 */
public class GetRoomFilterListMessageComposer extends MessageComposer {

    private final Set<String> filter;

    /**
     * Creates a get room filter list message composer instance for the network message subsystem.
     *
     * @param filter Filter supplied by the caller.
     */
    public GetRoomFilterListMessageComposer(final Set<String> filter) {
        this.filter = filter;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GetRoomFilterListMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(filter.size());

        for (String word : this.filter) {
            msg.writeString(word);
        }
    }
}
