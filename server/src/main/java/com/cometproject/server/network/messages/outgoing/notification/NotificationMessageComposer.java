package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Serializes the notification message for the Pixel Protocol client.
 */
public class NotificationMessageComposer extends MessageComposer {
    private static final String MESSAGE_KEY = "message";
    private static final String DISPLAY_KEY = "display";
    private static final String BUBBLE_DISPLAY = "BUBBLE";
    private static final String LINK_URL = "linkUrl";

    private final Map<String, String> parameters;
    private final String type;

    /**
     * Creates a notification message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     * @param parameters Parameters supplied by the caller.
     */
    public NotificationMessageComposer(final String type, final Map<String, String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    /**
     * Creates a notification message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public NotificationMessageComposer(final String type, final String message) {
        this(type, new HashMap<String, String>() {{
            put(DISPLAY_KEY, BUBBLE_DISPLAY);
            put(MESSAGE_KEY, message);
        }});
    }

    /**
     * Creates a notification message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     * @param message Message supplied by the caller.
     * @param event Event supplied by the caller.
     */
    public NotificationMessageComposer(final String type, final String message, final String event) {
        this(type, new HashMap<String, String>() {{
            put(DISPLAY_KEY, BUBBLE_DISPLAY);
            put(MESSAGE_KEY, message);
            put(LINK_URL, event);
        }});
    }

    /**
     * Creates a notification message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     */
    public NotificationMessageComposer(final String type) {
        this(type, Maps.newHashMap());
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
        msg.writeString(type);

        if (parameters == null || parameters.size() == 0) {
            msg.writeInt(0);
        } else {
            msg.writeInt(parameters.size());

            for (Map.Entry<String, String> param : parameters.entrySet()) {
                msg.writeString(param.getKey());
                msg.writeString(param.getValue());
            }
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {

    }
}
