package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the user name change message for the Pixel Protocol client.
 */
public class UserNameChangeMessageComposer extends MessageComposer {
    private int roomId;
    private int playerId;
    private String username;

    /**
     * Creates a user name change message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @param username Username supplied by the caller.
     */
    public UserNameChangeMessageComposer(int roomId, int playerId, String username) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.username = username;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserNameChangeMessageComposer;
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
        msg.writeString(username);
    }
}
