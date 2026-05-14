package com.cometproject.server.network.websockets.packets.outgoing.alerts;

/**
 * Describes youtube web packet behavior for the networking subsystem.
 */
public class YoutubeWebPacket {
    private String handle;
    private String url;

    /**
     * Creates a youtube web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param url Url supplied by the caller.
     */
    public YoutubeWebPacket(String handle, String url) {
        this.handle = handle;
        this.url = url;
    }

}
