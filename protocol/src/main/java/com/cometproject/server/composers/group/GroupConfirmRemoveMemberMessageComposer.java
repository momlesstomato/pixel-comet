package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the group confirm remove member message for the Pixel Protocol client.
 */
public class GroupConfirmRemoveMemberMessageComposer extends MessageComposer {
    private final int playerId;
    private final int furniCount;

    /**
     * Creates a group confirm remove member message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player id value supplied by the caller.
     * @param furniCount Furni count value supplied by the caller.
     */
    public GroupConfirmRemoveMemberMessageComposer(int playerId, int furniCount) {
        this.playerId = playerId;
        this.furniCount = furniCount;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    public short getId() {
        return Composers.GroupConfirmRemoveMemberMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Msg value supplied by the caller.
     */
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
        msg.writeInt(this.furniCount);
    }
}
