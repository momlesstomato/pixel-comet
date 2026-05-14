package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the talk message for the Pixel Protocol client.
 */
public class TalkMessageComposer extends MessageComposer {
    private String message;

    /**
     * Creates a talk message composer instance for the tooling subsystem.
     *
     * @param message Message supplied by the caller.
     */
    public TalkMessageComposer(String message) {
        this.message = message;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.ShoutMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(message);
        msg.writeInt(0);
    }
}
