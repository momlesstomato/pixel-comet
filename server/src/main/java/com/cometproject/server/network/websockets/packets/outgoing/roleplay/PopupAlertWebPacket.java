package com.cometproject.server.network.websockets.packets.outgoing.roleplay;

/**
 * Describes popup alert web packet behavior for the networking subsystem.
 */
public class PopupAlertWebPacket {
    private String handle;
    private String badge;
    private String popupTitle;
    private String hotelAlert;

    /**
     * Creates a popup alert web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param badge Badge supplied by the caller.
     * @param popupTitle Popup title supplied by the caller.
     * @param hotelAlert Hotel alert supplied by the caller.
     */
    public PopupAlertWebPacket(String handle, String badge, String popupTitle, String hotelAlert) {
        this.handle = handle;
        this.badge = badge;
        this.popupTitle = popupTitle;
        this.hotelAlert = hotelAlert;
    }

}