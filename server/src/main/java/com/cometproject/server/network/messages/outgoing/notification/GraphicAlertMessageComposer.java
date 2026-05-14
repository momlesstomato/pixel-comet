package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the graphic alert message for the Pixel Protocol client.
 */
public class GraphicAlertMessageComposer extends MessageComposer {
    private final String link;

    /**
     * Creates a graphic alert message composer instance for the network message subsystem.
     *
     * @param link Link supplied by the caller.
     */
    public GraphicAlertMessageComposer(final String link) {
        this.link = link;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GraphicAlertMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("${image.library.url}" + this.link + ".png");
    }
}
