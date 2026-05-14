package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room player unbanned message for the Pixel Protocol client.
 */
public class RoomPlayerUnbannedMessageComposer extends MessageComposer {
    private final int roomId;
    private final int playerId;

    /**
     * Creates a room player unbanned message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     */
    public RoomPlayerUnbannedMessageComposer(int roomId, int playerId) {
        this.roomId = roomId;
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UnbanUserFromRoomMessageComposer;
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
