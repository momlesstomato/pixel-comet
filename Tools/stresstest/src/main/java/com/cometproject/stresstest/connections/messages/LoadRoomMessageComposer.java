package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the load room message for the Pixel Protocol client.
 */
public class LoadRoomMessageComposer extends MessageComposer {

    private int roomId;
    private String password;

    /**
     * Creates a load room message composer instance for the tooling subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param password Password supplied by the caller.
     */
    public LoadRoomMessageComposer(int roomId, String password) {
        this.roomId = roomId;
        this.password = password;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.OpenFlatConnectionMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeString(password);
    }
}
