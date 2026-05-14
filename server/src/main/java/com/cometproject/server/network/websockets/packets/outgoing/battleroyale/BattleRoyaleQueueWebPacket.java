package com.cometproject.server.network.websockets.packets.outgoing.battleroyale;

import com.cometproject.server.game.rooms.types.components.games.survival.types.QueueData;

import java.util.ArrayList;

/**
 * Describes battle royale queue web packet behavior for the networking subsystem.
 */
public class BattleRoyaleQueueWebPacket {
    private String handle;
    private String roomId;
    private ArrayList<QueueData> figures;
    private String username;

    /**
     * Creates a battle royale queue web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param figures Figures supplied by the caller.
     */
    public BattleRoyaleQueueWebPacket(String handle, int roomId, ArrayList<QueueData> figures) {
        this.handle = handle;
        this.roomId = roomId + "";
        this.figures = figures;
        this.username = "Custom";
    }

}
