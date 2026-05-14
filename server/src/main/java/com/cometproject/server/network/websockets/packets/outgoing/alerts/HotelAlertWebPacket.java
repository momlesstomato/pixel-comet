package com.cometproject.server.network.websockets.packets.outgoing.alerts;

/**
 * Describes hotel alert web packet behavior for the networking subsystem.
 */
public class HotelAlertWebPacket {
    private String handle;
    private String figure;
    private String hotelAlert;

    /**
     * Creates a hotel alert web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param hotelAlert Hotel alert supplied by the caller.
     */
    public HotelAlertWebPacket(String handle, String figure, String hotelAlert) {
        this.handle = handle;
        this.figure = figure;
        this.hotelAlert = hotelAlert;
    }

}
