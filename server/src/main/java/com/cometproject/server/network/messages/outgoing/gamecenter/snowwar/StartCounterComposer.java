package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes start counter composer behavior for the network message subsystem.
 */
public class StartCounterComposer extends MessageComposer {
    private final int time;

    /**
     * Creates a start counter composer instance for the network message subsystem.
     *
     * @param time Time supplied by the caller.
     */
    public StartCounterComposer(int time) {
        this.time = time;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.time);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormStartLobbyCounterComposer;
    }
}