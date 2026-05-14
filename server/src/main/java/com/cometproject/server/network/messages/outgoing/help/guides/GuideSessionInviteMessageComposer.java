package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session invite message for the Pixel Protocol client.
 */
public class GuideSessionInviteMessageComposer extends MessageComposer {
    private final int roomId;
    private final String roomName;

    /**
     * Creates a guide session invite message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param roomName Room name supplied by the caller.
     */
    public GuideSessionInviteMessageComposer(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionInvitedToGuideRoomMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.roomId);
        msg.writeString(this.roomName);
    }
}