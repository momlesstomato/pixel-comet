package com.cometproject.server.network.websockets.packets.outgoing;

/**
 * Describes roulette end web packet behavior for the networking subsystem.
 */
public class RouletteEndWebPacket {
    private String handle;

    /**
     * Creates a roulette end web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     */
    public RouletteEndWebPacket(String handle) {
        this.handle = handle;
    }

}
