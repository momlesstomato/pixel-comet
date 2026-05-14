package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the update ignore status message for the Pixel Protocol client.
 */
public class UpdateIgnoreStatusMessageComposer extends MessageComposer {
    private final int result;
    private final String username;

    /**
     * Creates a update ignore status message composer instance for the network message subsystem.
     *
     * @param result Result supplied by the caller.
     * @param username Username supplied by the caller.
     */
    public UpdateIgnoreStatusMessageComposer(final int result, final String username) {
        this.result = result;
        this.username = username;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.IgnoreStatusMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(result);
        msg.writeString(username);
    }
}
