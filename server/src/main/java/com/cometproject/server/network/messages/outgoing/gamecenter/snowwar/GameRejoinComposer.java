package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game rejoin composer behavior for the network message subsystem.
 */
public class GameRejoinComposer extends MessageComposer {
    private final int roomId;

    /**
     * Creates a game rejoin composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     */
    public GameRejoinComposer(int roomId) {
        this.roomId = roomId;
    }
    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.roomId);
    }
    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }
}