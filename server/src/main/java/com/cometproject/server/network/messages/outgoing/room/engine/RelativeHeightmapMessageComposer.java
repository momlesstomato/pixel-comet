package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the relative heightmap message for the Pixel Protocol client.
 */
public class RelativeHeightmapMessageComposer extends MessageComposer {
    private final IRoomModel model;

    /**
     * Creates a relative heightmap message composer instance for the network message subsystem.
     *
     * @param model Model supplied by the caller.
     */
    public RelativeHeightmapMessageComposer(final IRoomModel model) {
        this.model = model;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FloorHeightMapMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true); // ??
        msg.writeInt(model.getRoomModelData().getWallHeight()); // wall-height
        msg.writeString(model.getRelativeHeightmap());
    }
}
