package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes enter arena failed composer behavior for the network message subsystem.
 */
public class EnterArenaFailedComposer extends MessageComposer {
    private final int errorCode;

    /**
     * Creates a enter arena failed composer instance for the network message subsystem.
     *
     * @param errorCode Error code supplied by the caller.
     */
    public EnterArenaFailedComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.errorCode);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormGenericErrorComposer;
    }
}