package com.cometproject.server.network.websockets.packets.outgoing.alerts;

/**
 * Describes ha web packet behavior for the networking subsystem.
 */
public class HAWebPacket {
    private String handle;
    private String figure;
    private String sender;
    private String hotelAlert;

    /**
     * Creates a ha web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param sender Sender supplied by the caller.
     * @param hotelAlert Hotel alert supplied by the caller.
     */
    public HAWebPacket(String handle, String figure, String sender, String hotelAlert) {
        this.handle = handle;
        this.figure = figure;
        this.sender = sender;
        this.hotelAlert = hotelAlert;
    }

}
