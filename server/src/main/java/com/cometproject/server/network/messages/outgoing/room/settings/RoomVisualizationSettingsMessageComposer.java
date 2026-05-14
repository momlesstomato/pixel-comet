package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room visualization settings message for the Pixel Protocol client.
 */
public class RoomVisualizationSettingsMessageComposer extends MessageComposer {
    private final boolean hideWall;
    private final int wallThick;
    private final int floorThick;

    /**
     * Creates a room visualization settings message composer instance for the network message subsystem.
     *
     * @param hideWall Hide wall supplied by the caller.
     * @param wallThick Wall thick supplied by the caller.
     * @param floorThick Floor thick supplied by the caller.
     */
    public RoomVisualizationSettingsMessageComposer(boolean hideWall, int wallThick, int floorThick) {
        this.hideWall = hideWall;
        this.wallThick = wallThick;
        this.floorThick = floorThick;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomVisualizationSettingsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(hideWall);
        msg.writeInt(wallThick);
        msg.writeInt(floorThick);
    }
}
