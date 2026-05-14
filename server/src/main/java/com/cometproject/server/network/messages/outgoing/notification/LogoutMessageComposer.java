package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the logout message for the Pixel Protocol client.
 */
public class LogoutMessageComposer extends MessageComposer {
    private final int reason;

    /**
     * Creates a logout message composer instance for the network message subsystem.
     *
     * @param reason Reason supplied by the caller.
     */
    public LogoutMessageComposer(final String reason) {
        if (reason.equals("banned")) {
            this.reason = 1;
        } else {
            this.reason = 0;
        }
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

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.reason);
    }
}
