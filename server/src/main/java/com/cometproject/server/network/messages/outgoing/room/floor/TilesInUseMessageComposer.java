package com.cometproject.server.network.messages.outgoing.room.floor;

import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.ArrayList;
import java.util.List;


/**
 * Serializes the tiles in use message for the Pixel Protocol client.
 */
public class TilesInUseMessageComposer extends MessageComposer {
    private final List<Position> tiles;

    /**
     * Creates a tiles in use message composer instance for the network message subsystem.
     *
     * @param tiles Tiles supplied by the caller.
     */
    public TilesInUseMessageComposer(final List<Position> tiles) {
        this.tiles = tiles;
    }

    /**
     * Creates a tiles in use message composer instance for the network message subsystem.
     */
    public TilesInUseMessageComposer() {
        this.tiles = new ArrayList<>();
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FloorPlanFloorMapMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(tiles.size());

        for (Position position : tiles) {
            msg.writeInt(position.getX());
            msg.writeInt(position.getY());
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.tiles.clear();
    }
}
