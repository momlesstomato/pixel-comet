package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the alert message for the Pixel Protocol client.
 */
public class AlertMessageComposer extends MessageComposer {
    private final String message;
    private final String link;

    /**
     * Creates a alert message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     * @param link Link supplied by the caller.
     */
    public AlertMessageComposer(final String message, final String link) {
        this.message = message;
        this.link = link;
    }

    /**
     * Creates a alert message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     */
    public AlertMessageComposer(final String message) {
        this(message, "");
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BroadcastMessageAlertMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.message);
        msg.writeString(this.link);
    }
}
