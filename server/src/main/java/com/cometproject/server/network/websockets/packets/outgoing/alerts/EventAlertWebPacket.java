package com.cometproject.server.network.websockets.packets.outgoing.alerts;

/**
 * Describes event alert web packet behavior for the networking subsystem.
 */
public class EventAlertWebPacket {
    private String handle;
    private String figure;
    private String eventHost;
    private String eventName;
    private String roomId;

    /**
     * Creates a event alert web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param eventHost Event host supplied by the caller.
     * @param eventName Event name supplied by the caller.
     * @param roomId Room identifier used by the operation.
     */
    public EventAlertWebPacket(String handle, String figure, String eventHost, String eventName, String roomId) {
        this.handle = handle;
        this.figure = figure;
        this.eventHost = eventHost;
        this.eventName = eventName;
        this.roomId = roomId;
    }

}
