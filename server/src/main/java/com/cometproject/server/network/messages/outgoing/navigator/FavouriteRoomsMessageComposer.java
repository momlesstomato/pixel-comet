package com.cometproject.server.network.messages.outgoing.navigator;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Set;


/**
 * Serializes the favourite rooms message for the Pixel Protocol client.
 */
public class FavouriteRoomsMessageComposer extends MessageComposer {
    private static final int MAX_FAVOURITE_ROOMS = 30;

    private final Set<Integer> favouriteRooms;

    /**
     * Creates a favourite rooms message composer instance for the network message subsystem.
     *
     * @param favouriteRooms Favourite rooms supplied by the caller.
     */
    public FavouriteRoomsMessageComposer(final Set<Integer> favouriteRooms) {
        this.favouriteRooms = favouriteRooms;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FavouritesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(MAX_FAVOURITE_ROOMS);
        msg.writeInt(this.favouriteRooms.size());//size

        for (int roomId : this.favouriteRooms) {
            msg.writeInt(roomId);
        }
    }
}
