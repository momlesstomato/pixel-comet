package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the wired alert message for the Pixel Protocol client.
 */
public class WiredAlertMessageComposer extends MessageComposer {
    private final String title;
    private final String alert;

    /**
     * Creates a wired alert message composer instance for the network message subsystem.
     *
     * @param title Title supplied by the caller.
     * @param alert Alert supplied by the caller.
     */
    public WiredAlertMessageComposer(final String title, final String alert) {
        this.title = title;
        this.alert = alert;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WiredAlertMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.title);
        msg.writeString(this.alert);
    }
}
