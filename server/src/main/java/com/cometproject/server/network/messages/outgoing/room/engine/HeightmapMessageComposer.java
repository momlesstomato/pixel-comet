package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.game.rooms.models.RoomTileState;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the heightmap message for the Pixel Protocol client.
 */
public class HeightmapMessageComposer extends MessageComposer {
    private final Room room;

    /**
     * Creates a heightmap message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public HeightmapMessageComposer(final Room room) {
        this.room = room;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.HeightMapMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(room.getModel().getSizeX());
        msg.writeInt(room.getModel().getSizeX() * room.getModel().getSizeY());

        for (int y = 0; y < room.getModel().getSizeY(); y++) {
            for (int x = 0; x < room.getModel().getSizeX(); x++) {
                RoomTile tile = room.getMapping().getTile(x, y);

                if (room.getModel().getSquareState()[x][y] == RoomTileState.INVALID) {
                    msg.writeShort(16191);
//                } else if (room.getModel().getDoorY() == y && room.getModel().getDoorX() == x) {
//                    msg.writeShort(0);
                } else {
                    if (tile != null) {
                        int height = (int) ((tile.getStackHeight()) * 256);

                        msg.writeShort(height);
                    } else {
                        msg.writeShort((short) room.getModel().getSquareHeight()[x][y] * 256);
                    }
                }
            }
        }
    }
}
