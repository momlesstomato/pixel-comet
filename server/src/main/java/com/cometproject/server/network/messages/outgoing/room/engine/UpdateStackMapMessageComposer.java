package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the update stack map message for the Pixel Protocol client.
 */
public class UpdateStackMapMessageComposer extends MessageComposer {
    private final List<RoomTile> tilesToUpdate;
    private final RoomTile singleTile;

    /**
     * Creates a update stack map message composer instance for the network message subsystem.
     *
     * @param tilesToUpdate Tiles to update supplied by the caller.
     */
    public UpdateStackMapMessageComposer(final List<RoomTile> tilesToUpdate) {
        this.tilesToUpdate = tilesToUpdate;
        this.singleTile = null;
    }

    /**
     * Creates a update stack map message composer instance for the network message subsystem.
     *
     * @param tile Tile supplied by the caller.
     */
    public UpdateStackMapMessageComposer(RoomTile tile) {
        this.tilesToUpdate = null;
        this.singleTile = tile;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UpdateStackMapMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeByte(singleTile != null ? 1 : tilesToUpdate.size());

        if (singleTile != null) {
            this.composeUpdate(this.singleTile, msg);
            return;
        }

        for (RoomTile tile : tilesToUpdate) {
            this.composeUpdate(tile, msg);
        }
    }

    private void composeUpdate(RoomTile tile, IComposer msg) {
        msg.writeByte(tile.getPosition().getX());
        msg.writeByte(tile.getPosition().getY());

        msg.writeShort((int) ((tile.getStackHeight()) * 256));
    }
}
