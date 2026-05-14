package com.cometproject.server.network.messages.outgoing.room.queue;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the room queue status message for the Pixel Protocol client.
 */
public class RoomQueueStatusMessageComposer extends MessageComposer {

    private final int playersWaiting;

    /**
     * Creates a room queue status message composer instance for the network message subsystem.
     *
     * @param playersWaiting Players waiting supplied by the caller.
     */
    public RoomQueueStatusMessageComposer(int playersWaiting) {
        this.playersWaiting = playersWaiting;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomQueueStatusMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(2);
        {
            msg.writeString("visitors");
            msg.writeInt(2);

            msg.writeInt(1);
            {
                msg.writeString("visitors");
                msg.writeInt(this.playersWaiting);
            }

            msg.writeString("spectators");
            msg.writeInt(1);

            msg.writeInt(1);
            {
                msg.writeString("spectators");
                msg.writeInt(0);
            }
        }

    }
}
