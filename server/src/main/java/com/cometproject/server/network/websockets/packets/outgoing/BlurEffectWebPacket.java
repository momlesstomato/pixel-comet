package com.cometproject.server.network.websockets.packets.outgoing;

/**
 * Describes blur effect web packet behavior for the networking subsystem.
 */
public class BlurEffectWebPacket {
    private String handle;

    /**
     * Creates a blur effect web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     */
    public BlurEffectWebPacket(String handle) {
        this.handle = handle;
    }

}
