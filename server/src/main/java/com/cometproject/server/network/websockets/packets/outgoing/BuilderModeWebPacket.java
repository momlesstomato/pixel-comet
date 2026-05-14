package com.cometproject.server.network.websockets.packets.outgoing;

/**
 * Describes builder mode web packet behavior for the networking subsystem.
 */
public class BuilderModeWebPacket {
    private String handle;

    /**
     * Creates a builder mode web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     */
    public BuilderModeWebPacket(String handle) {
        this.handle = handle;
    }

}
