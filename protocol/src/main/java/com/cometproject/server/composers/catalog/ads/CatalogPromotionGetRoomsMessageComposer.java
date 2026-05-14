package com.cometproject.server.composers.catalog.ads;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the catalog promotion get rooms message for the Pixel Protocol client.
 */
public class CatalogPromotionGetRoomsMessageComposer extends MessageComposer {
    private final List<IRoomData> promotableRooms;

    /**
     * Creates a catalog promotion get rooms message composer instance for the catalog subsystem.
     *
     * @param rooms Rooms value supplied by the caller.
     */
    public CatalogPromotionGetRoomsMessageComposer(final List<IRoomData> rooms) {
        this.promotableRooms = rooms;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.PromotableRoomsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false);
        msg.writeInt(this.promotableRooms.size());

        for (IRoomData data : this.promotableRooms) {
            msg.writeInt(data.getId());
            msg.writeString(data.getName());
            msg.writeBoolean(false);
        }
    }
}
