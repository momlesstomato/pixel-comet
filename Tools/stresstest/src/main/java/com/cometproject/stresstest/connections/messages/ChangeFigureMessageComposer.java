package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the change figure message for the Pixel Protocol client.
 */
public class ChangeFigureMessageComposer extends MessageComposer {
    private String figure;

    /**
     * Creates a change figure message composer instance for the tooling subsystem.
     *
     * @param figure Figure supplied by the caller.
     */
    public ChangeFigureMessageComposer(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.UpdateFigureDataMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("m");
        msg.writeString(this.figure);
    }
}
