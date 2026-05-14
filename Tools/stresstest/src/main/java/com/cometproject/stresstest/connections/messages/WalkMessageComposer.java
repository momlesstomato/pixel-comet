package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the walk message for the Pixel Protocol client.
 */
public class WalkMessageComposer extends MessageComposer {
    private int x;
    private int y;

    /**
     * Creates a walk message composer instance for the tooling subsystem.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public WalkMessageComposer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.MoveAvatarMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(x);
        msg.writeInt(y);
    }
}
