package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the ltd countdown message for the Pixel Protocol client.
 */
public class LTDCountdownMessageComposer extends MessageComposer {

    private final String c;
    private int flush;

    /**
     * Creates a ltd countdown message composer instance for the network message subsystem.
     *
     * @param c C supplied by the caller.
     * @param s S supplied by the caller.
     */
    public LTDCountdownMessageComposer(String c, int s) {
        this.c = c;
        this.flush = s;
    }


    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.LTDCountdownMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.c);
        msg.writeInt(this.flush);
    }
}
