package com.cometproject.server.network.websockets.packets.outgoing.alerts;

/**
 * Describes interactiv notification web packet behavior for the networking subsystem.
 */
public class InteractivNotificationWebPacket {
    private String handle;
    private String type;
    private String username;
    private String category;
    private String text;
    private String incoming;

    /**
     * Creates a interactiv notification web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param type Type supplied by the caller.
     * @param username Username supplied by the caller.
     * @param category Category supplied by the caller.
     * @param text Text supplied by the caller.
     * @param incoming Incoming supplied by the caller.
     */
    public InteractivNotificationWebPacket(String handle, String type, String username, String category, String text, String incoming) {
        this.handle = handle;
        this.type = type;
        this.username = username;
        this.category = category;
        this.text = text;
        this.incoming = incoming;
    }
}
