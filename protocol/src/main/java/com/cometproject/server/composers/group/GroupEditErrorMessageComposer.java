package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes a group edit error packet for the Pixel Protocol client.
 */
public class GroupEditErrorMessageComposer extends MessageComposer {
    private final int errorId;

    /**
     * Creates a group edit error message composer instance for the protocol composer subsystem.
     *
     * @param errorId Error id value supplied by the caller.
     */
    public GroupEditErrorMessageComposer(final int errorId) {
        this.errorId = errorId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GroupEditErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.errorId);
    }
}
