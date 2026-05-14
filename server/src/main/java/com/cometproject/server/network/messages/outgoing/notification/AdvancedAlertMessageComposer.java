package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the advanced alert message for the Pixel Protocol client.
 */
public class AdvancedAlertMessageComposer extends MessageComposer {
    private final String title;
    private final String message;
    private final String linkName;
    private final String linkLocation;
    private final String illustration;

    /**
     * Creates a advanced alert message composer instance for the network message subsystem.
     *
     * @param title Title supplied by the caller.
     * @param message Message supplied by the caller.
     * @param linkName Link name supplied by the caller.
     * @param linkLocation Link location supplied by the caller.
     * @param illustration Illustration supplied by the caller.
     */
    public AdvancedAlertMessageComposer(final String title, final String message, final String linkName, final String linkLocation, final String illustration) {
        this.title = title;
        this.message = message;
        this.linkName = linkName;
        this.linkLocation = linkLocation;
        this.illustration = illustration;
    }

    /**
     * Creates a advanced alert message composer instance for the network message subsystem.
     *
     * @param header Header supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public AdvancedAlertMessageComposer(final String header, final String message) {
        this(header, message, "", "", "");
    }

    /**
     * Creates a advanced alert message composer instance for the network message subsystem.
     *
     * @param header Header supplied by the caller.
     * @param message Message supplied by the caller.
     * @param image Image supplied by the caller.
     */
    public AdvancedAlertMessageComposer(final String header, final String message, final String image) {
        this(header, message, "", "", image);
    }

    /**
     * Creates a advanced alert message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     */
    public AdvancedAlertMessageComposer(String message) {
        this("Alert", message, "", "", "");
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(illustration);

        msg.writeInt(linkLocation.isEmpty() ? 2 : 4);
        msg.writeString("title");
        msg.writeString(title);
        msg.writeString("message");
        msg.writeString(message);

        if (!linkLocation.isEmpty()) {
            msg.writeString("linkUrl");
            msg.writeString(linkLocation);
            msg.writeString("linkTitle");
            msg.writeString(linkName);
        }
    }
}
