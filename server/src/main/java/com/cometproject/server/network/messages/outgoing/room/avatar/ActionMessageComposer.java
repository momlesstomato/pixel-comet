package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the action message for the Pixel Protocol client.
 */
public class ActionMessageComposer extends MessageComposer {
    private final int playerId;
    private final int actionId;

    /**
     * Creates a action message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param actionId Action id supplied by the caller.
     */
    public ActionMessageComposer(final int playerId, final int actionId) {
        this.playerId = playerId;
        this.actionId = actionId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ActionMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(actionId);
    }
}