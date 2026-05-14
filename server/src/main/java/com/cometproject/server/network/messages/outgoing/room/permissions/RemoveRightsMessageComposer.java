package com.cometproject.server.network.messages.outgoing.room.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the remove rights message for the Pixel Protocol client.
 */
public class RemoveRightsMessageComposer extends MessageComposer {
    private final int playerId;
    private final int roomId;

    /**
     * Creates a remove rights message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     */
    public RemoveRightsMessageComposer(final int playerId, final int roomId) {
        this.playerId = playerId;
        this.roomId = roomId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FlatControllerRemovedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(playerId);
    }
}
