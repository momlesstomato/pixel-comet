package com.cometproject.server.network.websockets.packets.outgoing.battleroyale;

/**
 * Describes battle royale reset queue web packet behavior for the networking subsystem.
 */
public class BattleRoyaleResetQueueWebPacket {
    private String handle;

    /**
     * Creates a battle royale reset queue web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     */
    public BattleRoyaleResetQueueWebPacket(String handle) {
        this.handle = handle;
    }

}
