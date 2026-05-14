package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the motd notification message for the Pixel Protocol client.
 */
public class MotdNotificationMessageComposer extends MessageComposer {
    private final String message;

    /**
     * Creates a motd notification message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     */
    public MotdNotificationMessageComposer(final String message) {
        this.message = message;
    }

    /**
     * Creates a motd notification message composer instance for the network message subsystem.
     */
    public MotdNotificationMessageComposer() {
        this(CometSettings.motdMessage);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MOTDNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);
        msg.writeString(message);
    }
}
