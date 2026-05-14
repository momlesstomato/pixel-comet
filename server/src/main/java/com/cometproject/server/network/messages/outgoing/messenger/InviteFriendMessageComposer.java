package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the invite friend message for the Pixel Protocol client.
 */
public class InviteFriendMessageComposer extends MessageComposer {
    private final String message;
    private final int fromId;

    /**
     * Creates a invite friend message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     * @param fromId From id supplied by the caller.
     */
    public InviteFriendMessageComposer(final String message, final int fromId) {
        this.message = message;
        this.fromId = fromId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomInviteMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(fromId);
        msg.writeString(message);
    }
}
