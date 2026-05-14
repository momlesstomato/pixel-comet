package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session message message for the Pixel Protocol client.
 */
public class GuideSessionMessageMessageComposer extends MessageComposer {
    private final String message;
    private final int playerId;

    /**
     * Creates a guide session message message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public GuideSessionMessageMessageComposer(String message, int playerId) {
        this.message = message;
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionMessageMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.message);
        msg.writeInt(this.playerId);
    }
}