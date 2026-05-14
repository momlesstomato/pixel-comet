package com.cometproject.server.network.messages.outgoing.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the home room message for the Pixel Protocol client.
 */
public class HomeRoomMessageComposer extends MessageComposer {
    private final int roomId;
    private final int newRoom;

    /**
     * Creates a home room message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param newRoom New room supplied by the caller.
     */
    public HomeRoomMessageComposer(final int roomId, final int newRoom) {
        this.roomId = roomId;
        this.newRoom = newRoom;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NavigatorSettingsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.roomId);
        msg.writeInt(this.newRoom);
    }
}
